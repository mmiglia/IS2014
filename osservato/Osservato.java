package osservato;

public interface Osservato
{
	int registra(Osservatore o);
	void rimuovi(int id);
	void nuovoEvento(Evento e);
}
