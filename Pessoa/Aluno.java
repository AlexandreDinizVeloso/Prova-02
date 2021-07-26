package Pessoa;

public class Aluno extends Pessoa{
    public Aluno(int id, String name, String cpf, String tipo){
        super(id, name, cpf);
        this.tipo = tipo;
    }
}
