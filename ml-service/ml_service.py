from pathlib import Path
from typing import List

import joblib
import numpy as np
from fastapi import FastAPI, HTTPException
from fastapi.middleware.cors import CORSMiddleware
from pydantic import BaseModel, ConfigDict


BASE_DIR = Path(__file__).resolve().parent

app = FastAPI(title="Fraud ML Service", version="1.0.0")
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

model = joblib.load(BASE_DIR / "rf_fraud_model.pkl")
scaler = joblib.load(BASE_DIR / "rf_scaler.pkl")
explainer = joblib.load(BASE_DIR / "shap_explainer.pkl")

FEATURE_NAMES = [
    "amount",
    "hour",
    "day_of_week",
    "amount_diff",
    "time_diff",
    "amount_velocity",
    "rolling_avg",
    "deviation",
]


class PredictionRequest(BaseModel):
    amount: float
    hour: int
    day_of_week: int
    amount_diff: float
    time_diff: float
    amount_velocity: float
    rolling_avg: float
    deviation: float


class PredictionResponse(BaseModel):
    model_config = ConfigDict(populate_by_name=True)

    fraud_score: float
    is_fraud: bool
    explanation: List[str]


def get_shap_values(current_explainer, data: np.ndarray) -> np.ndarray:
    shap_values = current_explainer.shap_values(data)
    if isinstance(shap_values, list):
        values = shap_values[1]
    else:
        values = shap_values
    if len(values.shape) == 3:
        values = values[:, :, 1]
    return values


@app.get("/health")
def health() -> dict:
    return {"status": "ok"}


@app.post("/predict", response_model=PredictionResponse)
def predict(data: PredictionRequest) -> PredictionResponse:
    try:
        values = [
            data.amount,
            data.hour,
            data.day_of_week,
            data.amount_diff,
            data.time_diff,
            data.amount_velocity,
            data.rolling_avg,
            data.deviation,
        ]

        features = np.array([values], dtype=float)
        scaled_features = scaler.transform(features)

        prediction = bool(model.predict(scaled_features)[0])
        probability = float(model.predict_proba(scaled_features)[0][1])
        shap_values = get_shap_values(explainer, scaled_features)

        explanation = []
        for index, shap_value in enumerate(shap_values[0]):
            direction = "increases risk" if float(shap_value) > 0 else "reduces risk"
            explanation.append(f"{FEATURE_NAMES[index]} {direction}")

        return PredictionResponse(
            fraud_score=probability,
            is_fraud=prediction,
            explanation=explanation,
        )
    except Exception as exc:
        raise HTTPException(status_code=500, detail=f"Prediction failed: {exc}") from exc
