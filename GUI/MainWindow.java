package GUI;

import java.awt.*;
import javax.swing.*;
import DataBase.DataBase;
import Pessoa.Aluno;
import Pessoa.Funcionario;
import Pessoa.Pessoa;

import java.awt.event.*;
//import DataBase.DataBase;

public class MainWindow extends JPanel implements ActionListener{
    private String window;
    private DataBase db;

    //listWindow variables
    final JButton regButton;
    final JButton altButton;
    final JButton delButton;

    //regWindow variables
    final JLabel idLabel;
    final JTextField idField;
    final JLabel nameLabel;
    final JTextField nameField;
    final JLabel cpfLabel;
    final JTextField cpfField;
    final JLabel typeLabel;
    final JComboBox typeField;

    private JTable dataTable;

    final String[] types = {"Aluno", "Professor", "Cozinheiro", "Faxineiro"};
    final String[] data = {"Id", "Nome", "CPF", "Tipo"};

    final JButton saveButton;
    final JButton cleanButton;
    final JButton cancelButton;

    public MainWindow(){
        window = "";
        db = new DataBase();

        regButton = new JButton("Registrar Novo");
        altButton = new JButton("Alterar Selecionado");
        delButton = new JButton("Excluir Selecionado");

        idLabel = new JLabel("Id:");
        idField = new JTextField();
        nameLabel = new JLabel("Nome:");
        nameField = new JTextField();
        cpfLabel = new JLabel("CPF:");
        cpfField = new JTextField();
        typeLabel = new JLabel("Tipo:"); 
        typeField = new JComboBox<>(types);

        loadDataGrid();

        saveButton = new JButton("Salvar");
        cleanButton = new JButton("Limpar");
        cancelButton = new JButton("Cancelar");

        this.setSize(800, 600);
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.setLayout(new FlowLayout());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == regButton){
            idField.setEditable(true);
            idField.setText(Integer.toString(db.getLastId()+1));
            idField.setEditable(false);
            changeWindow();
        }
        if (e.getSource() == altButton){
            idField.setEditable(true);
            idField.setText(Integer.toString(db.getLastId()));
            idField.setEditable(false);
            fillAlter();
            changeWindow();
        }
        if (e.getSource() == cancelButton){
            cleanFields();
            changeWindow();
        }
        if (e.getSource() == saveButton){
            saveRegister();
            loadDataGrid();
            changeWindow();
            cleanFields();
        }
        if (e.getSource() == cleanButton){
            cleanFields();
        }
    }

    public void listWindow(){
        window = "list";

        regButton.addActionListener(this);
        altButton.addActionListener(this);
        delButton.addActionListener(this);

        dataTable.setPreferredSize(new Dimension(700, 500));
        regButton.setPreferredSize(new Dimension(200, 25));
        altButton.setPreferredSize(new Dimension(200, 25));
        delButton.setPreferredSize(new Dimension(200, 25));

        this.add(dataTable);
        this.add(regButton);
        this.add(altButton);
        this.add(delButton);
    }

    public void regWindow(){
        window = "reg";

        typeField.setEditable(false);
        saveButton.addActionListener(this);
        cleanButton.addActionListener(this);
        cancelButton.addActionListener(this);

        idLabel.setPreferredSize(new Dimension(50, 25));
        idField.setPreferredSize(new Dimension(300, 25));
        nameLabel.setPreferredSize(new Dimension(50, 25));
        nameField.setPreferredSize(new Dimension(300, 25));
        cpfLabel.setPreferredSize(new Dimension(50, 25));
        cpfField.setPreferredSize(new Dimension(300, 25));
        typeLabel.setPreferredSize(new Dimension(50, 25));
        typeField.setPreferredSize(new Dimension(300, 25));
        saveButton.setPreferredSize(new Dimension(200, 25));
        cleanButton.setPreferredSize(new Dimension(200, 25));
        cancelButton.setPreferredSize(new Dimension(200, 25));

        this.add(idLabel);
        this.add(idField);
        this.add(nameLabel);
        this.add(nameField);
        this.add(cpfLabel);
        this.add(cpfField);
        this.add(typeLabel);
        this.add(typeField);
        this.add(saveButton);
        this.add(cleanButton);
        this.add(cancelButton);
    }

    final void saveRegister(){
        Pessoa temp;

        if (typeField.getSelectedItem().toString() == "Aluno"){
            temp = new Aluno(Integer.parseInt(idField.getText()), nameField.getText(), cpfField.getText(), typeField.getSelectedItem().toString());
        }
        else{
            temp = new Funcionario(Integer.parseInt(idField.getText()), nameField.getText(), cpfField.getText(), typeField.getSelectedItem().toString());
        }

        if (db.getLastId()+1 != temp.getId()){
            for (Pessoa i:db.getDb()){
                if (i.getId() == temp.getId()){
                    db.altRegister(i.getId(), temp);
                    return;
                }
            }
        }
        else db.addRegister(temp);
    }

    final void fillAlter(){
        int row = dataTable.getSelectedRow();
        
        idField.setText(dataTable.getModel().getValueAt(row, 0).toString());
        nameField.setText(dataTable.getModel().getValueAt(row, 1).toString());
        cpfField.setText(dataTable.getModel().getValueAt(row, 2).toString());
        for (String i:data){
            if (i == dataTable.getModel().getValueAt(row, 3).toString()){
                typeField.setSelectedItem((Object) i);       
            }
        }
    }

    final void loadDataGrid(){
        try{
            dataTable = new JTable(db.getTable(), data);
            altButton.setEnabled(true);
            delButton.setEnabled(true);
        }
        catch (NegativeArraySizeException e){
            dataTable = new JTable(new Object[1][4], data);
            altButton.setEnabled(false);
            delButton.setEnabled(false);
            e.printStackTrace();
        }
    }

    final void changeWindow(){
        if (window == "list"){
            removeAll();
            repaint();
            regWindow();
            revalidate();
        }
        else if (window == "reg"){
            removeAll();
            repaint();
            listWindow();
            revalidate();
        }
    }

    final void deleteItem(){
        int row = dataTable.getSelectedRow();
        int temp = Integer.parseInt(dataTable.getModel().getValueAt(row, 0).toString());

        db.remRegister(temp);
    }

    final void cleanFields(){
        nameField.setText("");
        cpfField.setText("");
    }

    /**
     * @return JButton return the regButton
     */
    public JButton getRegButton() {
        return regButton;
    }

    /**
     * @return JButton return the altButton
     */
    public JButton getAltButton() {
        return altButton;
    }

    /**
     * @return JButton return the delButton
     */
    public JButton getDelButton() {
        return delButton;
    }

    /**
     * @return JLabel return the idLabel
     */
    public JLabel getIdLabel() {
        return idLabel;
    }

    /**
     * @return JTextField return the idField
     */
    public JTextField getIdField() {
        return idField;
    }

    /**
     * @return JLabel return the nameLabel
     */
    public JLabel getNameLabel() {
        return nameLabel;
    }

    /**
     * @return JTextField return the nameField
     */
    public JTextField getNameField() {
        return nameField;
    }

    /**
     * @return JLabel return the cpfLabel
     */
    public JLabel getCpfLabel() {
        return cpfLabel;
    }

    /**
     * @return JTextField return the cpfField
     */
    public JTextField getCpfField() {
        return cpfField;
    }

    /**
     * @return JLabel return the typeLabel
     */
    public JLabel getTypeLabel() {
        return typeLabel;
    }

    /**
     * @return String[] return the types
     */
    public String[] getTypes() {
        return types;
    }

    /**
     * @return JButton return the saveButton
     */
    public JButton getSaveButton() {
        return saveButton;
    }
    /**
     * @return JButton return the cleanButton
     */
    public JButton getCleanButton() {
        return cleanButton;
    }

    /**
     * @return JButton return the cancelButton
     */
    public JButton getCancelButton() {
        return cancelButton;
    }
}