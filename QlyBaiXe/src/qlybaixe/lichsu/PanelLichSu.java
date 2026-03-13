package qlybaixe.lichsu;

import java.awt.*;
import java.io.*;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import qlybaixe.dao.BookingDAO;
import qlybaixe.dashboard.Dashboard;
import qlybaixe.model.User;

/**
 * Lịch sử đặt sân – chuẩn NetBeans JPanel Form
 */
public class PanelLichSu extends javax.swing.JPanel {

    private static final Color PRIMARY    = Dashboard.PRIMARY;
    private static final Color BG         = new Color(245, 246, 250);
    private static final Color WHITE      = Color.WHITE;
    private static final Color TEXT_DARK  = Dashboard.TEXT_DARK;
    private static final Color TEXT_GRAY  = Dashboard.TEXT_GRAY;
    private static final Color BORDER_CLR = Dashboard.BORDER_CLR;

    private static final DecimalFormat     FMT = new DecimalFormat("#,###");
    private static final DateTimeFormatter DF  = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter HM  = DateTimeFormatter.ofPattern("HH:mm");

    private final BookingDAO bookingDAO = new BookingDAO();

    // Stat chips — update sau search
    private JLabel chipTotal, chipPaid, chipWait, chipCancel;
    private JLabel lblResultCount;

    // Current data for export
    private List<Object[]> lastData;

    public PanelLichSu(User user, Dashboard dash) {
        initComponents();
        initRuntimeComponents();
        setupUI();
    }
    public PanelLichSu() { this(null, null); }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
    }// </editor-fold>//GEN-END:initComponents

    private void initRuntimeComponents() {
        setLayout(new BorderLayout());
        setBackground(BG);
        removeAll();

        lblTitle = new JLabel();
        lblSubtitle = new JLabel();
        panelFilter = new JPanel();
        panelStats = new JPanel();
        scrollTable = new JScrollPane();
        tblHistory = new JTable();
        spnFrom = new JSpinner();
        spnTo = new JSpinner();
        cmbStatus = new JComboBox();
        txtSearch = new JTextField();
        btnSearch = new JButton();
        btnClear = new JButton();
        btnExport = new JButton();
    }

    private void setupUI() {
        // ══════════════════════════════════════════════════
        // HEADER: title + filter card
        // ══════════════════════════════════════════════════
        JPanel header = new JPanel();
        header.setBackground(BG);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBorder(new EmptyBorder(24, 28, 0, 28));
        add(header, BorderLayout.NORTH);

        lblTitle.setText("Lịch sử đặt sân");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(TEXT_DARK);
        lblTitle.setAlignmentX(LEFT_ALIGNMENT);

        lblSubtitle.setText("Tra cứu tất cả các đơn đặt sân trong hệ thống");
        lblSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblSubtitle.setForeground(TEXT_GRAY);
        lblSubtitle.setAlignmentX(LEFT_ALIGNMENT);

        header.add(lblTitle);
        header.add(Box.createVerticalStrut(3));
        header.add(lblSubtitle);
        header.add(Box.createVerticalStrut(16));

        // ── Filter card ───────────────────────────────────
        panelFilter.setBackground(WHITE);
        panelFilter.setBorder(new CompoundBorder(
            new LineBorder(BORDER_CLR, 1, true),
            new EmptyBorder(16, 20, 16, 20)));
        panelFilter.setLayout(new BoxLayout(panelFilter, BoxLayout.Y_AXIS));
        panelFilter.setAlignmentX(LEFT_ALIGNMENT);
        panelFilter.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

        // Row 1: date + status + search
        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        row1.setOpaque(false);

        // Date pickers
        Calendar cFrom = Calendar.getInstance(); cFrom.add(Calendar.DAY_OF_MONTH, -30);
        spnFrom.setModel(new SpinnerDateModel(cFrom.getTime(), null, null, Calendar.DAY_OF_MONTH));
        JSpinner.DateEditor deFrom = new JSpinner.DateEditor(spnFrom, "dd/MM/yyyy");
        spnFrom.setEditor(deFrom); spnFrom.setPreferredSize(new Dimension(130, 36));
        ((JSpinner.DefaultEditor) spnFrom.getEditor()).getTextField().setFont(new Font("Segoe UI", Font.PLAIN, 13));

        spnTo.setModel(new SpinnerDateModel());
        JSpinner.DateEditor deTo = new JSpinner.DateEditor(spnTo, "dd/MM/yyyy");
        spnTo.setEditor(deTo); spnTo.setPreferredSize(new Dimension(130, 36));
        ((JSpinner.DefaultEditor) spnTo.getEditor()).getTextField().setFont(new Font("Segoe UI", Font.PLAIN, 13));

        // Status dropdown
        cmbStatus.setModel(new DefaultComboBoxModel<>(new String[]{
            "Tất cả trạng thái","Chờ thanh toán","Đã thanh toán","Đã hủy","Hoàn thành"
        }));
        cmbStatus.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cmbStatus.setPreferredSize(new Dimension(190, 36));

        // Search
        txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtSearch.setPreferredSize(new Dimension(230, 36));
        txtSearch.putClientProperty("JTextField.placeholderText", "🔍  Tên khách, SĐT, mã đơn...");
        txtSearch.addActionListener(e -> doSearch());

        row1.add(makeLF("Từ ngày", spnFrom));
        row1.add(makeLF("Đến ngày", spnTo));
        row1.add(makeLF("Trạng thái", cmbStatus));
        row1.add(makeLF("Tìm kiếm", txtSearch));

        // Row 2: buttons
        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        row2.setOpaque(false);
        row2.setBorder(new EmptyBorder(10, 0, 0, 0));

        style(btnSearch, "🔍  Tìm kiếm", PRIMARY, WHITE, true);
        style(btnClear,  "↺  Đặt lại",   new Color(241,245,249), TEXT_DARK, false);
        style(btnExport, "⬇  Xuất CSV",  new Color(236,253,245), new Color(5,150,105), false);
        btnSearch.addActionListener(e -> doSearch());
        btnClear .addActionListener(e -> doClear());
        btnExport.addActionListener(e -> doExport());

        row2.add(btnSearch); row2.add(btnClear);
        row2.add(Box.createHorizontalStrut(20));
        row2.add(btnExport);

        panelFilter.add(row1);
        panelFilter.add(row2);
        header.add(panelFilter);

        // ══════════════════════════════════════════════════
        // CENTER: stat chips + table
        // ══════════════════════════════════════════════════
        JPanel center = new JPanel();
        center.setBackground(BG);
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBorder(new EmptyBorder(14, 28, 28, 28));
        add(center, BorderLayout.CENTER);

        // Stat chip row
        panelStats.setBackground(BG);
        panelStats.setLayout(new FlowLayout(FlowLayout.LEFT, 8, 0));
        panelStats.setMaximumSize(new Dimension(Integer.MAX_VALUE, 46));
        panelStats.setAlignmentX(LEFT_ALIGNMENT);

        chipTotal  = makeChip("Tổng đơn: —",    new Color(219,234,254), new Color(29,78,216));
        chipPaid   = makeChip("Đã thanh toán: —",new Color(220,252,231), new Color(22,163,74));
        chipWait   = makeChip("Chờ TT: —",       new Color(254,243,199), new Color(161,98,7));
        chipCancel = makeChip("Đã hủy: —",       new Color(254,226,226), new Color(220,38,38));
        lblResultCount = new JLabel();
        lblResultCount.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblResultCount.setForeground(TEXT_GRAY);

        panelStats.add(chipTotal); panelStats.add(chipPaid);
        panelStats.add(chipWait);  panelStats.add(chipCancel);
        panelStats.add(Box.createHorizontalStrut(10));
        panelStats.add(lblResultCount);
        center.add(panelStats);
        center.add(Box.createVerticalStrut(10));

        // Table card
        JPanel tableCard = new JPanel(new BorderLayout());
        tableCard.setBackground(WHITE);
        tableCard.setBorder(new LineBorder(BORDER_CLR, 1, true));
        tableCard.setAlignmentX(LEFT_ALIGNMENT);

        buildTable();
        scrollTable.setViewportView(tblHistory);
        scrollTable.setBorder(null);
        scrollTable.getViewport().setBackground(WHITE);
        tableCard.add(scrollTable);
        center.add(tableCard);
    }

    /** Helper: JPanel label+field */
    private JPanel makeLF(String label, JComponent field) {
        JPanel p = new JPanel(new BorderLayout(0, 4)); p.setOpaque(false);
        JLabel l = new JLabel(label); l.setFont(new Font("Segoe UI",Font.BOLD,10)); l.setForeground(TEXT_GRAY);
        p.add(l, BorderLayout.NORTH); p.add(field, BorderLayout.CENTER);
        return p;
    }

    /** Helper: style button */
    private void style(JButton b, String txt, Color bg, Color fg, boolean bold) {
        b.setText(txt); b.setBackground(bg); b.setForeground(fg);
        b.setFont(new Font("Segoe UI", bold?Font.BOLD:Font.PLAIN, 13));
        b.setBorderPainted(false); b.setFocusPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setPreferredSize(new Dimension(140, 34));
    }

    /** Helper: chip label */
    private JLabel makeChip(String txt, Color bg, Color fg) {
        JLabel l = new JLabel(txt);
        l.setFont(new Font("Segoe UI", Font.BOLD, 12));
        l.setForeground(fg); l.setBackground(bg); l.setOpaque(true);
        l.setBorder(new CompoundBorder(new LineBorder(bg.darker(),1,true), new EmptyBorder(5,12,5,12)));
        return l;
    }

    private void buildTable() {
        tblHistory.setModel(new DefaultTableModel(new Object[][]{},
            new String[]{"MÃ ĐƠN","KHÁCH HÀNG","SÂN","NGÀY CHƠI","GIỜ","TỔNG TIỀN","TRẠNG THÁI","NGƯỜI TẠO"}){
            @Override public boolean isCellEditable(int r, int c){ return false; }
        });
        tblHistory.setRowHeight(52);
        tblHistory.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblHistory.setShowGrid(false);
        tblHistory.setIntercellSpacing(new Dimension(0,0));
        tblHistory.setSelectionBackground(new Color(235,240,255));
        tblHistory.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 10));
        tblHistory.getTableHeader().setBackground(new Color(249,250,251));
        tblHistory.getTableHeader().setForeground(TEXT_GRAY);
        tblHistory.getTableHeader().setPreferredSize(new Dimension(0, 40));
        tblHistory.getTableHeader().setBorder(new MatteBorder(0,0,1,0, BORDER_CLR));

        // Column widths
        int[] w = {110,180,140,90,100,110,140,120};
        for (int i=0; i<w.length; i++) tblHistory.getColumnModel().getColumn(i).setPreferredWidth(w[i]);

        DefaultTableCellRenderer base = new DefaultTableCellRenderer(){
            @Override public Component getTableCellRendererComponent(JTable t,Object v,boolean sel,boolean foc,int r,int c){
                super.getTableCellRendererComponent(t,v,sel,foc,r,c);
                setFont(new Font("Segoe UI",Font.PLAIN,13));
                setBorder(new EmptyBorder(0,14,0,14));
                if(!sel){setBackground(r%2==0?WHITE:new Color(250,251,252));setForeground(TEXT_DARK);}
                return this;
            }
        };
        // Code column: bold + blue
        DefaultTableCellRenderer codeR = new DefaultTableCellRenderer(){
            @Override public Component getTableCellRendererComponent(JTable t,Object v,boolean sel,boolean foc,int r,int c){
                super.getTableCellRendererComponent(t,v,sel,foc,r,c);
                setFont(new Font("Segoe UI",Font.BOLD,12)); setForeground(new Color(29,78,216));
                setBorder(new EmptyBorder(0,14,0,14));
                if(!sel) setBackground(r%2==0?WHITE:new Color(250,251,252));
                return this;
            }
        };
        // Amount: right-aligned bold
        DefaultTableCellRenderer amtR = new DefaultTableCellRenderer(){
            @Override public Component getTableCellRendererComponent(JTable t,Object v,boolean sel,boolean foc,int r,int c){
                super.getTableCellRendererComponent(t,v,sel,foc,r,c);
                setFont(new Font("Segoe UI",Font.BOLD,13)); setHorizontalAlignment(RIGHT);
                setBorder(new EmptyBorder(0,14,0,14)); setForeground(new Color(5,150,105));
                if(!sel) setBackground(r%2==0?WHITE:new Color(250,251,252));
                return this;
            }
        };

        tblHistory.getColumnModel().getColumn(0).setCellRenderer(codeR);
        for (int i=1;i<5;i++) tblHistory.getColumnModel().getColumn(i).setCellRenderer(base);
        tblHistory.getColumnModel().getColumn(1).setCellRenderer(new DefaultTableCellRenderer(){
            @Override public Component getTableCellRendererComponent(JTable t,Object v,boolean sel,boolean foc,int r,int c){
                JPanel p=new JPanel(new BorderLayout()); p.setOpaque(true);
                p.setBackground(r%2==0?WHITE:new Color(250,251,252));
                p.setBorder(new EmptyBorder(4,14,4,14));
                String html=v==null?"":v.toString();
                JLabel l=new JLabel(html); l.setFont(new Font("Segoe UI",Font.PLAIN,13)); l.setForeground(TEXT_DARK);
                p.add(l,BorderLayout.CENTER); return p;
            }
        });
        tblHistory.getColumnModel().getColumn(5).setCellRenderer(amtR);
        // Status badge
        tblHistory.getColumnModel().getColumn(6).setCellRenderer((t,v,sel,foc,r,c)->{
            String s=v==null?"":v.toString(); Color bg,fg;
            if("Đã thanh toán".equals(s)||"Hoàn thành".equals(s)){bg=new Color(220,252,231);fg=new Color(22,163,74);}
            else if("Chờ thanh toán".equals(s)){bg=new Color(254,243,199);fg=new Color(161,98,7);}
            else if("Đã hủy".equals(s)){bg=new Color(254,226,226);fg=new Color(220,38,38);}
            else{bg=new Color(219,234,254);fg=new Color(29,78,216);}
            JLabel lbl=new JLabel(s,SwingConstants.CENTER);
            lbl.setFont(new Font("Segoe UI",Font.BOLD,11));
            lbl.setForeground(fg); lbl.setBackground(bg); lbl.setOpaque(true);
            lbl.setBorder(new EmptyBorder(4,10,4,10));
            JPanel wp=new JPanel(new FlowLayout(FlowLayout.CENTER,0,10));
            wp.setBackground(r%2==0?WHITE:new Color(250,251,252));
            wp.add(lbl); return wp;
        });
        tblHistory.getColumnModel().getColumn(7).setCellRenderer(base);
    }

    public void loadData() { doSearch(); }

    private void doSearch() {
        java.util.Date dFrom=(java.util.Date)spnFrom.getValue();
        java.util.Date dTo  =(java.util.Date)spnTo.getValue();
        LocalDate from=dFrom.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        LocalDate to  =dTo.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        String sel=(String)cmbStatus.getSelectedItem();
        String statusCode="Tất cả trạng thái".equals(sel)?"ALL"
            :"Chờ thanh toán".equals(sel)?"CHO_THANH_TOAN"
            :"Đã thanh toán".equals(sel) ?"DA_THANH_TOAN"
            :"Đã hủy".equals(sel)        ?"DA_HUY":"HOAN_THANH";
        String kw=txtSearch.getText().trim();
        btnSearch.setEnabled(false); btnSearch.setText("Đang tải...");

        new SwingWorker<List<Object[]>,Void>(){
            @Override protected List<Object[]> doInBackground() throws Exception{
                return bookingDAO.getBookingHistory(from,to,statusCode,kw);
            }
            @Override protected void done(){
                btnSearch.setEnabled(true); btnSearch.setText("🔍  Tìm kiếm");
                try{
                    lastData=get();
                    DefaultTableModel m=(DefaultTableModel)tblHistory.getModel(); m.setRowCount(0);
                    int paid=0,wait=0,cancel=0;
                    for(Object[] r:lastData){
                        String st=(String)r[9];
                        LocalDate bd=(LocalDate)r[5];
                        java.time.LocalTime bs=(java.time.LocalTime)r[6],be=(java.time.LocalTime)r[7];
                        java.math.BigDecimal amt=(java.math.BigDecimal)r[8];
                        String createdBy=r.length>10&&r[10]!=null?r[10].toString():"—";
                        m.addRow(new Object[]{
                            r[1],
                            "<html><b>"+r[2]+"</b><br><font color='gray' size='2'>"+r[3]+"</font></html>",
                            r[4], bd.format(DF), bs.format(HM)+"–"+be.format(HM),
                            FMT.format(amt)+" đ", fmtStatus(st), createdBy
                        });
                        if("DA_THANH_TOAN".equals(st)||"HOAN_THANH".equals(st)) paid++;
                        else if("CHO_THANH_TOAN".equals(st)) wait++;
                        else if("DA_HUY".equals(st)) cancel++;
                    }
                    if(m.getRowCount()==0)
                        m.addRow(new Object[]{"","Không tìm thấy kết quả nào","","","","","",""});
                    chipTotal .setText("Tổng đơn: "+lastData.size());
                    chipPaid  .setText("Đã thanh toán: "+paid);
                    chipWait  .setText("Chờ TT: "+wait);
                    chipCancel.setText("Đã hủy: "+cancel);
                    lblResultCount.setText("Tìm thấy "+lastData.size()+" đơn");
                }catch(Exception ex){
                    JOptionPane.showMessageDialog(PanelLichSu.this,"Lỗi: "+ex.getMessage());
                }
            }
        }.execute();
    }

    private void doClear(){
        Calendar c=Calendar.getInstance(); c.add(Calendar.DAY_OF_MONTH,-30);
        spnFrom.setValue(c.getTime()); spnTo.setValue(new java.util.Date());
        cmbStatus.setSelectedIndex(0); txtSearch.setText("");
        doSearch();
    }

    private void doExport(){
        if(lastData==null||lastData.isEmpty()){
            JOptionPane.showMessageDialog(this,"Chưa có dữ liệu để xuất.");return;
        }
        JFileChooser fc=new JFileChooser();
        fc.setDialogTitle("Lưu file CSV");
        fc.setSelectedFile(new File("lich_su_dat_san_"+LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))+".csv"));
        if(fc.showSaveDialog(this)!=JFileChooser.APPROVE_OPTION) return;
        try(PrintWriter pw=new PrintWriter(fc.getSelectedFile(),"UTF-8")){
            pw.println("\uFEFFMã đơn,Khách hàng,Số ĐT,Sân,Ngày,Giờ bắt đầu,Giờ kết thúc,Tổng tiền,Trạng thái");
            for(Object[] r:lastData){
                LocalDate bd=(LocalDate)r[5];
                java.time.LocalTime bs=(java.time.LocalTime)r[6],be=(java.time.LocalTime)r[7];
                java.math.BigDecimal amt=(java.math.BigDecimal)r[8];
                pw.println(String.join(",",
                    q(r[1]),q(r[2]),q(r[3]),q(r[4]),
                    bd.format(DF),bs.format(HM),be.format(HM),
                    FMT.format(amt),q(fmtStatus((String)r[9]))));
            }
            JOptionPane.showMessageDialog(this,"✅ Xuất "+lastData.size()+" đơn thành công!\n"+fc.getSelectedFile().getAbsolutePath());
        }catch(Exception ex){ JOptionPane.showMessageDialog(this,"Lỗi xuất file: "+ex.getMessage()); }
    }

    private String q(Object o){ return o==null?"":("\""+o.toString().replace("\"","'")+"\""); }

    private String fmtStatus(String s){
        if(s==null) return "";
        switch(s){ case "CHO_THANH_TOAN":return"Chờ thanh toán"; case "DA_THANH_TOAN":return"Đã thanh toán";
                   case "DA_HUY":return"Đã hủy"; case "HOAN_THANH":return"Hoàn thành"; default:return s; }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnExport;
    private javax.swing.JButton btnSearch;
    private javax.swing.JComboBox cmbStatus;
    private javax.swing.JLabel lblFromLbl;
    private javax.swing.JLabel lblSearchLbl;
    private javax.swing.JLabel lblStatCancel;
    private javax.swing.JLabel lblStatPaid;
    private javax.swing.JLabel lblStatTotal;
    private javax.swing.JLabel lblStatWait;
    private javax.swing.JLabel lblStatusLbl;
    private javax.swing.JLabel lblSubtitle;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblToLbl;
    private javax.swing.JPanel panelFilter;
    private javax.swing.JPanel panelStats;
    private javax.swing.JScrollPane scrollTable;
    private javax.swing.JSpinner spnFrom;
    private javax.swing.JSpinner spnTo;
    private javax.swing.JTable tblHistory;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
