package qlybaixe.datlich;

import java.awt.*;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.*;
import javax.swing.table.*;
import qlybaixe.dao.BookingDAO;
import qlybaixe.dashboard.Dashboard;
import qlybaixe.model.User;
import qlybaixe.util.RoundedButton;

/**
 * JPanel – nội dung "Đặt lịch" nhúng vào Dashboard.
 * Chuẩn NetBeans JPanel Form.
 */
public class PanelDatLich extends javax.swing.JPanel {

    private static final Color PRIMARY   = Dashboard.PRIMARY;
    private static final Color TEXT_DARK = Dashboard.TEXT_DARK;
    private static final Color TEXT_GRAY = Dashboard.TEXT_GRAY;
    private static final Color BORDER    = Dashboard.BORDER_CLR;

    private static final DecimalFormat FMT     = new DecimalFormat("#,###");
    private static final DateTimeFormatter DF  = DateTimeFormatter.ofPattern("d/M/yyyy");
    private static final DateTimeFormatter HM  = DateTimeFormatter.ofPattern("HH:mm");

    private final BookingDAO bookingDAO = new BookingDAO();
    private final User       currentUser;
    private final Dashboard  dashboard;

    private final java.util.List<Long>   rowIds      = new java.util.ArrayList<>();
    private final java.util.List<String> rowStatuses = new java.util.ArrayList<>();

    public PanelDatLich(User user, Dashboard dash) {
        this.currentUser = user;
        this.dashboard   = dash;
        initComponents();
        setupAfterInit();
    }

    public PanelDatLich() { this(null, null); }

    private void setupAfterInit() {
        setBackground(new Color(245, 246, 250));
        btnTaoDon.addActionListener(e -> openTaoDon());
        btnTaoDon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnTaoDon.setContentAreaFilled(true);

        initTableModel();
        styleTableRenderers();
        scrollTable.getViewport().setBackground(Color.WHITE);
        scrollTable.setBorder(BorderFactory.createLineBorder(BORDER));

        loadData();
    }

    private void initTableModel() {
        tblBookings.setModel(new DefaultTableModel(
            new Object[][]{},
            new String[]{
                "MÃ ĐƠN", "KHÁCH HÀNG", "SÂN", "NGÀY",
                "GIỜ", "TỔNG TIỀN", "TRẠNG THÁI", "THAO TÁC"
            }
        ) {
            @Override public boolean isCellEditable(int row, int column) {
                return column == 7;
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitle = new javax.swing.JLabel();
        lblSubtitle = new javax.swing.JLabel();
        btnTaoDon = new javax.swing.JButton();
        scrollTable = new javax.swing.JScrollPane();
        tblBookings = new javax.swing.JTable();

        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(30, 41, 59));
        lblTitle.setText("Quản lý đặt sân");

        lblSubtitle.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblSubtitle.setForeground(new java.awt.Color(107, 114, 128));
        lblSubtitle.setText("Danh sách tất cả các đơn đặt sân");

        btnTaoDon.setBackground(new java.awt.Color(13, 71, 161));
        btnTaoDon.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnTaoDon.setForeground(new java.awt.Color(255, 255, 255));
        btnTaoDon.setText("+ Tạo đơn mới");
        btnTaoDon.setBorderPainted(false);
        btnTaoDon.setFocusPainted(false);

        tblBookings.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        tblBookings.setRowHeight(52);
        tblBookings.setShowGrid(false);
        scrollTable.setViewportView(tblBookings);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblTitle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnTaoDon, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblSubtitle)
                    .addComponent(scrollTable, javax.swing.GroupLayout.PREFERRED_SIZE, 853, Short.MAX_VALUE))
                .addGap(24, 24, 24))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lblTitle)
                    .addComponent(btnTaoDon, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addComponent(lblSubtitle)
                .addGap(14, 14, 14)
                .addComponent(scrollTable, javax.swing.GroupLayout.PREFERRED_SIZE, 421, Short.MAX_VALUE)
                .addGap(24, 24, 24))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void openTaoDon() {
        if (dashboard != null) dashboard.showPanel(Dashboard.PANEL_TAO_DON);
    }

    // ── Load data ────────────────────────────────────────────────
    public void loadData() {
        new SwingWorker<List<Object[]>, Void>() {
            @Override protected List<Object[]> doInBackground() throws Exception {
                return bookingDAO.getAllBookingRows();
            }
            @Override protected void done() {
                try {
                    List<Object[]> rows = get();
                    DefaultTableModel m = (DefaultTableModel) tblBookings.getModel();
                    m.setRowCount(0);
                    rowIds.clear(); rowStatuses.clear();
                    for (Object[] row : rows) {
                        long id      = (Long)   row[0];
                        String code  = (String) row[1];
                        String name  = (String) row[2];
                        String phone = (String) row[3];
                        String court = (String) row[4];
                        java.time.LocalDate date  = (java.time.LocalDate) row[5];
                        java.time.LocalTime start = (java.time.LocalTime) row[6];
                        java.time.LocalTime end   = (java.time.LocalTime) row[7];
                        java.math.BigDecimal total= (java.math.BigDecimal) row[8];
                        String status= (String) row[9];
                        rowIds.add(id); rowStatuses.add(status);
                        m.addRow(new Object[]{
                            code,
                            "<html><b>"+name+"</b><br><font color='gray' size='2'>"+phone+"</font></html>",
                            court, date.format(DF),
                            start.format(HM)+" – "+end.format(HM),
                            FMT.format(total)+" đ",
                            qlybaixe.dashboard.PanelTongQuan.formatStatus(status),
                            "action"
                        });
                    }
                    if (m.getRowCount()==0)
                        m.addRow(new Object[]{"","Chưa có đơn đặt sân nào","","","","","",""});
                } catch(Exception ex) {
                    JOptionPane.showMessageDialog(PanelDatLich.this,
                        "Lỗi tải dữ liệu: "+ex.getMessage());
                }
            }
        }.execute();
    }

    // ── Style table ──────────────────────────────────────────────
    private void styleTableRenderers() {
        int[] w = {120,180,140,80,100,100,110,90};
        for(int i=0;i<w.length;i++) tblBookings.getColumnModel().getColumn(i).setPreferredWidth(w[i]);

        DefaultTableCellRenderer base = new DefaultTableCellRenderer() {
            @Override public Component getTableCellRendererComponent(JTable t,Object v,boolean sel,boolean foc,int r,int c) {
                super.getTableCellRendererComponent(t,v,sel,foc,r,c);
                setFont(new Font("Segoe UI",Font.PLAIN,13));
                setBorder(javax.swing.BorderFactory.createEmptyBorder(0,12,0,12));
                if(!sel){setBackground(r%2==0?Color.WHITE:new Color(250,251,252));setForeground(TEXT_DARK);}
                return this;
            }
        };
        for(int i=0;i<6;i++) tblBookings.getColumnModel().getColumn(i).setCellRenderer(base);

        // Status badge
        tblBookings.getColumnModel().getColumn(6).setCellRenderer(new TableCellRenderer() {
            @Override public Component getTableCellRendererComponent(JTable t,Object v,boolean sel,boolean foc,int r,int c) {
                String txt = v==null?"":v.toString();
                Color bg,fg;
                if("Đã thanh toán".equals(txt)){bg=new Color(220,252,231);fg=new Color(22,163,74);}
                else if("Chờ thanh toán".equals(txt)){bg=new Color(254,243,199);fg=new Color(161,98,7);}
                else if("Đã hủy".equals(txt)){bg=new Color(254,226,226);fg=new Color(220,38,38);}
                else{bg=new Color(219,234,254);fg=new Color(29,78,216);}
                JLabel lbl=new JLabel(txt,SwingConstants.CENTER);
                lbl.setFont(new Font("Segoe UI",Font.BOLD,11));
                lbl.setForeground(fg);lbl.setBackground(bg);lbl.setOpaque(true);
                lbl.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                    javax.swing.BorderFactory.createLineBorder(fg.brighter().brighter(),1,true),
                    javax.swing.BorderFactory.createEmptyBorder(3,10,3,10)));
                JPanel wp=new JPanel(new FlowLayout(FlowLayout.CENTER,0,10));
                wp.setBackground(r%2==0?Color.WHITE:new Color(250,251,252));
                wp.add(lbl); return wp;
            }
        });

        // Action buttons
        tblBookings.getColumnModel().getColumn(7).setCellRenderer(new ActionCellRenderer());
        tblBookings.getColumnModel().getColumn(7).setCellEditor(new ActionCellEditor(this));
    }

    void confirmPayment(int row) {
        if(row<0||row>=rowIds.size()) return;
        long id=rowIds.get(row); String stat=rowStatuses.get(row);
        if(!"CHO_THANH_TOAN".equals(stat)){
            JOptionPane.showMessageDialog(this,"Đơn này không ở trạng thái chờ thanh toán."); return;}
        if(JOptionPane.showConfirmDialog(this,"Xác nhận đã thu tiền mặt?","Xác nhận",
                JOptionPane.YES_NO_OPTION)!=JOptionPane.YES_OPTION) return;
        long cby=(currentUser!=null&&currentUser.getId()!=null)?currentUser.getId():1L;
        try{bookingDAO.confirmPayment(id,cby);
            JOptionPane.showMessageDialog(this,"✅ Xác nhận thanh toán thành công!");
            loadData();
        }catch(Exception ex){JOptionPane.showMessageDialog(this,"Lỗi: "+ex.getMessage());}
    }

    void cancelBooking(int row) {
        if(row<0||row>=rowIds.size()) return;
        long id=rowIds.get(row); String stat=rowStatuses.get(row);
        if("DA_HUY".equals(stat)){JOptionPane.showMessageDialog(this,"Đơn này đã bị hủy rồi."); return;}
        if("DA_THANH_TOAN".equals(stat)){JOptionPane.showMessageDialog(this,"Đơn đã thanh toán, không thể hủy.","Cảnh báo",JOptionPane.WARNING_MESSAGE); return;}
        if(JOptionPane.showConfirmDialog(this,"Bạn có chắc muốn hủy đơn này?","Xác nhận",
                JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE)!=JOptionPane.YES_OPTION) return;
        try{bookingDAO.cancelBooking(id);
            JOptionPane.showMessageDialog(this,"Đã hủy đơn thành công.");
            loadData();
        }catch(Exception ex){JOptionPane.showMessageDialog(this,"Lỗi: "+ex.getMessage());}
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnTaoDon;
    private javax.swing.JLabel lblSubtitle;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JScrollPane scrollTable;
    private javax.swing.JTable tblBookings;
    // End of variables declaration//GEN-END:variables
}

// ── ActionCellRenderer ──────────────────────────────────────────
class ActionCellRenderer implements TableCellRenderer {
    private final JPanel  p   = new JPanel(new FlowLayout(FlowLayout.CENTER,8,8));
    private final JLabel  ok  = new JLabel("✅");
    private final JLabel  del = new JLabel("❌");
    ActionCellRenderer() {
        p.setOpaque(true);
        ok .setFont(new Font("Segoe UI Emoji",Font.PLAIN,18));
        del.setFont(new Font("Segoe UI Emoji",Font.PLAIN,18));
        p.add(ok); p.add(del);
    }
    @Override public Component getTableCellRendererComponent(JTable t,Object v,boolean sel,boolean foc,int r,int c) {
        p.setBackground(r%2==0?Color.WHITE:new Color(250,251,252)); return p;
    }
}

// ── ActionCellEditor ────────────────────────────────────────────
class ActionCellEditor extends DefaultCellEditor {
    private final PanelDatLich parent;
    private final JPanel  p   = new JPanel(new FlowLayout(FlowLayout.CENTER,8,8));
    private final JLabel  ok  = new JLabel("✅");
    private final JLabel  del = new JLabel("❌");
    private int curRow = -1;
    ActionCellEditor(PanelDatLich parent) {
        super(new JCheckBox());
        this.parent = parent;
        setClickCountToStart(1);
        p.setBackground(Color.WHITE);
        ok .setFont(new Font("Segoe UI Emoji",Font.PLAIN,18));
        del.setFont(new Font("Segoe UI Emoji",Font.PLAIN,18));
        ok .setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        del.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        ok.addMouseListener(new java.awt.event.MouseAdapter(){
            @Override public void mouseClicked(java.awt.event.MouseEvent e){stopCellEditing();parent.confirmPayment(curRow);}});
        del.addMouseListener(new java.awt.event.MouseAdapter(){
            @Override public void mouseClicked(java.awt.event.MouseEvent e){stopCellEditing();parent.cancelBooking(curRow);}});
        p.add(ok); p.add(del);
    }
    @Override public Component getTableCellEditorComponent(JTable t,Object v,boolean sel,int r,int c){
        curRow=r; p.setBackground(r%2==0?Color.WHITE:new Color(250,251,252)); return p;}
    @Override public Object getCellEditorValue(){return null;}
}
