package com.example.demo.infrastructure.client.openweathermap.model;

import com.example.demo.domain.model.Weather;

import java.util.List;

public record WeatherResponse(List<Weather> weather) {
}
