package Pessoa;

public class Funcionario extends Pessoa{
    public Funcionario(int id, String name, String cpf, String tipo){
        super(id, name, cpf);
        this.tipo = tipo;
    }
}
