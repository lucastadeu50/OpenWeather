package com.example.lucas.openweather.model;

import java.io.Serializable;
import java.util.List;

/**
 * Pojo que foi criado para receer a resposa da api
 */

public class Feed implements Serializable {
	private int dt;
	private List<WeatherItem> weather;
	private String name;
	private int cod;
	private Main main;
	private int id;
	private String base;

	public int getDt() {
		return dt;
	}

	public void setDt(int dt) {
		this.dt = dt;
	}

	public List<WeatherItem> getWeather() {
		return weather;
	}

	public void setWeather(List<WeatherItem> weather) {
		this.weather = weather;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCod() {
		return cod;
	}

	public void setCod(int cod) {
		this.cod = cod;
	}

	public Main getMain() {
		return main;
	}

	public void setMain(Main main) {
		this.main = main;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	@Override
	public String toString() {
		return "Feed{" +
				"dt=" + dt +
				", weather=" + weather +
				", name='" + name + '\'' +
				", cod=" + cod +
				", main=" + main +
				", id=" + id +
				", base='" + base + '\'' +
				'}';
	}
}