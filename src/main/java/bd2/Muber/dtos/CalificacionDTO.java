package bd2.Muber.dtos;

import bd2.Muber.model.Calificacion;

public class CalificacionDTO {

	private String pasajero;
	private Integer puntaje;
	private String comentario;
	private Integer viajeID;
	
	public CalificacionDTO(Calificacion calificacion) {
		this.pasajero = calificacion.getPasajero().getUsuario();
		this.puntaje = calificacion.getPuntaje();
		this.comentario = calificacion.getComentario();
		this.viajeID = calificacion.getViaje().getId();
	}
	
	public String getPasajero() {
		return pasajero;
	}
	public void setPasajero(String pasajero) {
		this.pasajero = pasajero;
	}
	public Integer getPuntaje() {
		return puntaje;
	}
	public void setPuntaje(Integer puntaje) {
		this.puntaje = puntaje;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	public Integer getViajeID() {
		return viajeID;
	}
	public void setViajeID(Integer viajeID) {
		this.viajeID = viajeID;
	}
}