package com.restlet.course.project.clients;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.restlet.course.project.Player;

public class RestletClient{
	public static void main(String[] args) {
		try {
			Gson gson = new Gson();
			mostrarTodosLosJugadores();
			Player newPlayer = new Player("Juancito");
			Representation response = new ClientResource("http://localhost:8182/players").post(newPlayer);
			newPlayer = gson.fromJson(response.getText(), Player.class);
			System.out.println("Creando el jugador juancito");
			System.out.println(newPlayer);
			mostrarTodosLosJugadores();
			System.out.println("Modificando Jugador "+newPlayer);
			String nombreNuevo = "Armando";
			System.out.println("a nombre: "+nombreNuevo);
			newPlayer.setUsername(nombreNuevo);
			response = new ClientResource("http://localhost:8182/players").put(newPlayer);
			System.out.println("Jugador Modificado");
			mostrarTodosLosJugadores();
			System.out.println("Eliminando Jugador "+newPlayer);
			response = new ClientResource("http://localhost:8182/player/"+newPlayer.getId()).delete();
			System.out.println("Jugador eliminado");
			mostrarTodosLosJugadores();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	private static void mostrarTodosLosJugadores() {
		Gson gson = new Gson();
		Representation response = new ClientResource("http://localhost:8182/players").get();
		Type playerListType = new TypeToken<ArrayList<Player>>(){}.getType();

		System.out.println("Los jugadores: ");
		try {
			List<Player> players = gson.fromJson(response.getText(), playerListType);
			for (Player player : players) {
				System.out.println(player);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
}
