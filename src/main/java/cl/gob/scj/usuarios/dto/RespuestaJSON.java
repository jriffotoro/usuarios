package cl.gob.scj.usuarios.dto;

import java.util.List;

public class RespuestaJSON {
    
    public enum EstadoType {	
        
        OK ("success", 0),
        ERROR ("error", 1);

        private final String respuestaJSONS;
        private final int respuestaJSONI;
    
        private EstadoType(String respuestaJSONS, int respuestaJSONI) {
            this.respuestaJSONS = respuestaJSONS;
            this.respuestaJSONI = respuestaJSONI;
        }

        public String getRespuestaJSONS() {
            return respuestaJSONS;
        }

        public int getRespuestaJSONI() {
            return respuestaJSONI;
        }      
    
	}
	
	private int estado;
	private String mensaje;
	private Object adicional;
	private String tipoRespuesta;	
	
	public RespuestaJSON(){

    }
	
	public RespuestaJSON(int estado, String mensaje){
		this.estado = estado;
		this.mensaje = mensaje;
	}

	public RespuestaJSON(String tipoRespuesta, String mensaje){
		this.tipoRespuesta = tipoRespuesta;
		this.mensaje = mensaje;
	}

	public RespuestaJSON(String tipoRespuesta, String mensaje, Object adicional){
		this.tipoRespuesta = tipoRespuesta;
		this.mensaje = mensaje;
		this.adicional = adicional;
	}
		
	public RespuestaJSON(int estado, String mensaje, Object adicional){
		this.estado = estado;
		this.mensaje = mensaje;
		this.adicional = adicional;
	}

	public RespuestaJSON(String tipoRespuesta, String mensaje, List<Object> adicional){
		this.tipoRespuesta = tipoRespuesta;
		this.mensaje = mensaje;
		this.adicional = adicional;
	}
	
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public Object getAdicional() {
		return adicional;
	}
	public void setAdicional(Object adicional) {
		this.adicional = adicional;
	}
	public String getTipoRespuesta() {
		return this.tipoRespuesta;
	}
	public void setTipoRespuesta(String tipoRespuesta) {
		this.tipoRespuesta = tipoRespuesta;
	}

}
