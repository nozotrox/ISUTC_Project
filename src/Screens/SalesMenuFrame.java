package Screens;

import Main.ID_Gen;
import Main.UserUtility;
import Main_Classes.Storage;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class SalesMenuFrame extends JInternalFrame implements ActionListener {


    public JTable table;
    public DefaultTableModel model;
    JComboBox<String> combo;
    JScrollPane sp;
    JPanel tablePanel;
    String[] columnsNames;

    private JTextField codigo_, stockMinimo_, qtd_, nome_, qtd_venda, preco, taxes, total;
    private JComboBox armazem_;
    private JButton save, update, btnPesquisar, calcular;
    private JCheckBox efectuarPesquisa;


    public SalesMenuFrame(){

        /*try{
            for(UIManager.LookAndFeelInfo info: UIManager.getInstalledLookAndFeels()){
                if("Nimbus".equals(info.getName())){
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }


        }catch(Exception e){
            e.printStackTrace();
        }*/

        build_ui();
    }

    public void build_ui(){

        setLocation(0,0);
        this.setTitle("Produtos");
        tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout(0, 0));
        this.setLayout(new BorderLayout(0, 0));

        JLabel title = new JLabel("Introducao de Produtos");
        columnsNames = new String[] { "Codigo", "Nome", "Stock Minimo", "Armazem", "Quantidade", "Stock Minimo" };
        model = new DefaultTableModel(null, columnsNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // TODO Auto-generated method stub
                return false;
            }
        };

        table = new JTable(model);
        table.setFillsViewportHeight(true);
        table.sizeColumnsToFit(1);
        populate_table();

        sp = new JScrollPane(table);
        tablePanel.add("North", table.getTableHeader());
        tablePanel.add("Center", sp);

        combo = new JComboBox<>();

        //::>> Setup Codigo
        codigo_ = new JTextField(7);
        codigo_.setEnabled(false);
        codigo_.setText("Undefined");

        //::>> Setup Armazem
        armazem_ = new JComboBox();

        //::>> Setup  Stock Minimo
        stockMinimo_ = new JTextField(7);

        //::>> Setup Quantidade
        qtd_ = new JTextField(7);
        JPanel qtd  = new JPanel(new FlowLayout(FlowLayout.LEFT));
        qtd.add(new JLabel("Quantidade:         "));
        qtd.add(qtd_);


        //::>> Setup NOme
        nome_ = new JTextField(20);

        //::>> Setup Fornecedor
        fillComboBox();

        //::>Setup Buttons
        save = new JButton("Gravar  ");
        save.addActionListener(this);
        update = new JButton("Actualizar");
        update.addActionListener(this);
        this.btnPesquisar = new JButton("Pesquisar");
        this.btnPesquisar.addActionListener(this);
        this.calcular = new JButton("Calcular");
        this.calcular.addActionListener(this);

        this.efectuarPesquisa = new JCheckBox("Efectura Pesquisa");
        setColumnSizes();

        //::>> Setup Selling elemnts
        total = new JTextField(20);
        qtd_venda = new JTextField(10);
        preco = new JTextField(10);
        total = new JTextField(10);
        total.setEnabled(false);
        taxes = new JTextField(10);

        /*Layout*/

        JPanel bd1 = new JPanel(new GridLayout(2,2, 10, 10));
        bd1.setPreferredSize(new Dimension(150, 60));
        JPanel bd2 = new JPanel(new GridLayout(2,2, 10, 10));
        JPanel bd3 = new JPanel(new GridLayout(2,2, 10, 10));
        JPanel bd4 = new JPanel(new GridLayout(2,1, 10, 10));
        JPanel bdLayout = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JPanel bd5 = new JPanel(new GridLayout(2,2, 10, 10));
        JPanel bd6 = new JPanel(new GridLayout(2,2, 10, 10));
        JPanel bd7 = new JPanel(new GridLayout(2,1, 10, 10));
        JPanel bdLayout2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bdLayout2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true), "Dados Venda"));


        JPanel data_fields = new JPanel(new BorderLayout());

        JLabel lbl2 = new JLabel("ID: ");
        lbl2.setHorizontalAlignment(JLabel.RIGHT);

        bd1.add(lbl2);
        bd1.add(this.codigo_);


        JLabel lbl1 = new JLabel("Nome: ");
        lbl1.setHorizontalAlignment(JLabel.RIGHT);

        bd1.add(lbl1);
        bd1.add(this.nome_);

        bd2.add(new JLabel("Quantidade: "));
        bd2.add(this.qtd_);

        bd2.add(new JLabel("Stock Minimo: "));
        bd2.add(this.stockMinimo_);

        bd3.add(new JLabel("Fornecedor: "));
        bd3.add(this.combo);

        bd3.add(new JLabel("Armazem: "));
        bd3.add(this.armazem_);

        bd4.add(this.btnPesquisar);
        bd4.add(this.efectuarPesquisa);


        bdLayout.add(bd1);
        bdLayout.add(bd2);
        bdLayout.add(bd3);
        bdLayout.add(bd4);


        bd5.add(new JLabel("Quantidade: ", JLabel.RIGHT));
        bd5.add(qtd_venda);
        bd5.add(new JLabel("Preco: ", JLabel.RIGHT));
        bd5.add(preco);

        bd6.add(new JLabel("Iva: ", JLabel.RIGHT));
        bd6.add(taxes);
        bd6.add(new JLabel("Total: ", JLabel.RIGHT));
        bd6.add(total);

        bd7.add(calcular);

        bd6.setPreferredSize(new Dimension(150, 63));

        bdLayout2.add(bd5);
        bdLayout2.add(bd6);
        bdLayout2.add(bd7);


        data_fields.add("North", bdLayout);
        data_fields.add("South", bdLayout2);

        this.add("North", data_fields);
        this.add("South", tablePanel);

        this.pack();
        this.setVisible(true);

    }
    @Override
    public void actionPerformed(ActionEvent e) {

    }


    public void fillComboBox(){
        String[][] fornecedores = UserUtility.active_user.getAllProviders();
        combo.addItem("Escolha o Fornecedor");
        for(String[] data: fornecedores){
            String nome = data[1];
            combo.addItem(nome);
        }

        String[][] armazens = UserUtility.active_user.getAllStorages();
        armazem_.addItem("Escolha o Armazem");
        for(String[] armazem: armazens){
            armazem_.addItem(armazem[1]);
        }

    }

    public void populate_table(){
        Vector<Storage> storages = UserUtility.active_user.getStorage();
        for(Storage storage: storages){

            String[][] data = storage.getAllProducts();

            for(String[] dt: data){
                model.addRow(dt);
            }

        }
    }


    public void setColumnSizes(){
        DefaultTableColumnModel columnModel = (DefaultTableColumnModel) this.table.getColumnModel();
        for(int i = 0; i < 6; i++){
            columnModel.getColumn(i).setMinWidth(100);
        }


    }
}
