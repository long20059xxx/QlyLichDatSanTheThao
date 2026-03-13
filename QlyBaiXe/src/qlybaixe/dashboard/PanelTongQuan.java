package qlybaixe.dashboard;

import java.awt.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.*;
import javax.swing.table.*;
import qlybaixe.dao.BookingDAO;
import qlybaixe.dao.CourtDAO;
import qlybaixe.dao.PaymentDAO;
import qlybaixe.model.User;
import qlybaixe.util.RoundedButton;

/**
 * JPanel – nội dung "Tổng quan" nhúng vào Dashboard.
 * Chuẩn NetBeans JPanel Form.
 */
public class PanelTongQuan extends javax.swing.JPanel {

    private static final Color PRIMARY   = Dashboard.PRIMARY;
    private static final Color TEXT_DARK = Dashboard.TEXT_DARK;
    private static final Color TEXT_GRAY = Dashboard.TEXT_GRAY;
    private static final Color BORDER    = Dashboard.BORDER_CLR;

    private static final DecimalFormat FMT   = new DecimalFormat("#,###");
    private static final DateTimeFormatter HM = DateTimeFormatter.ofPattern("HH:mm");

    private final BookingDAO bookingDAO = new BookingDAO();
    private final CourtDAO   courtDAO   = new CourtDAO();
    private final PaymentDAO paymentDAO = new PaymentDAO();
    private final User       currentUser;
    private final Dashboard  dashboard;

    // stat-card value labels
    private JLabel valDonHomNay, valDoanhThu, valSanHoatDong, valTongDon;

    public PanelTongQuan(User user, Dashboard dash) {
        this.currentUser = user;
        this.dashboard   = dash;
        initComponents();
        buildStatCards();
        buildGreeting();
    }

    public PanelTongQuan() { this(null, null); }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblGreeting    = new javax.swing.JLabel();
        lblSubtitle    = new javax.swing.JLabel();
        panelCards     = new javax.swing.JPanel();
        lblSectionTitle= new javax.swing.JLabel();
        scrollTable    = new javax.swing.JScrollPane();
        tblBookings    = new javax.swing.JTable();

        setBackground(new java.awt.Color(245, 246, 250));
        setLayout(null); // sẽ thay bằng GroupLayout bên dưới

        lblGreeting.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 22));
        lblGreeting.setForeground(TEXT_DARK);
        lblGreeting.setText("Xin chào! 👋");

        lblSubtitle.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));
        lblSubtitle.setForeground(TEXT_GRAY);
        lblSubtitle.setText("Hôm nay, " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        panelCards.setOpaque(false);
        panelCards.setLayout(new java.awt.GridLayout(1, 4, 16, 0));
        panelCards.setPreferredSize(new java.awt.Dimension(800, 110));

        lblSectionTitle.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 15));
        lblSectionTitle.setForeground(TEXT_DARK);
        lblSectionTitle.setText("Đơn đặt sân hôm nay");

        // Table
        tblBookings.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));
        tblBookings.setModel(new javax.swing.table.DefaultTableModel(
            new Object[][]{},
            new String[]{"MÃ ĐƠN","KHÁCH HÀNG","SÂN","GIỜ","TRẠNG THÁI","TỔNG TIỀN"}
        ) { public boolean isCellEditable(int r,int c){return false;} });
        tblBookings.setRowHeight(44);
        tblBookings.setShowGrid(false);
        tblBookings.setIntercellSpacing(new java.awt.Dimension(0,0));
        tblBookings.setSelectionBackground(new java.awt.Color(235,240,255));
        tblBookings.getTableHeader().setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 11));
        tblBookings.getTableHeader().setBackground(new java.awt.Color(249,250,251));
        tblBookings.getTableHeader().setForeground(TEXT_GRAY);
        tblBookings.getTableHeader().setPreferredSize(new java.awt.Dimension(0,38));
        scrollTable.setViewportView(tblBookings);
        scrollTable.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        scrollTable.getViewport().setBackground(java.awt.Color.WHITE);

        // Card wrapper
        javax.swing.JPanel tableCard = new javax.swing.JPanel(new java.awt.BorderLayout());
        tableCard.setBackground(java.awt.Color.WHITE);
        tableCard.setBorder(javax.swing.BorderFactory.createLineBorder(BORDER, 1, true));
        tableCard.add(scrollTable);

        // Root layout
        javax.swing.GroupLayout gl = new javax.swing.GroupLayout(this);
        setLayout(gl);
        gl.setHorizontalGroup(
            gl.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gl.createSequentialGroup()
                .addGap(24)
                .addGroup(gl.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblGreeting)
                    .addComponent(lblSubtitle)
                    .addComponent(panelCards,  javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblSectionTitle)
                    .addComponent(tableCard,   javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(24))
        );
        gl.setVerticalGroup(
            gl.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gl.createSequentialGroup()
                .addGap(24).addComponent(lblGreeting)
                .addGap(4) .addComponent(lblSubtitle)
                .addGap(20).addComponent(panelCards, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24).addComponent(lblSectionTitle)
                .addGap(10).addComponent(tableCard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(24))
        );
    }// </editor-fold>//GEN-END:initComponents

    // ── Build stat cards ─────────────────────────────────────────
    private void buildStatCards() {
        panelCards.removeAll();
        Object[][] cards = {
            {"Đơn hôm nay",    "—", new Color(219,234,254), new Color(37,99,235),  "📋"},
            {"Doanh thu hôm nay","—",new Color(220,252,231), new Color(22,163,74),  "💰"},
            {"Sân hoạt động",  "—", new Color(237,233,254), new Color(124,58,237), "🏟"},
            {"Tổng đơn",       "—", new Color(254,243,199), new Color(161,98,7),   "📊"},
        };
        JLabel[] valueLabels = new JLabel[4];
        for (int i = 0; i < cards.length; i++) {
            Object[] c = cards[i];
            JPanel card = new JPanel();
            card.setBackground(Color.WHITE);
            card.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(BORDER, 1, true),
                javax.swing.BorderFactory.createEmptyBorder(16, 20, 16, 20)));
            card.setLayout(new BorderLayout(0, 8));

            // Top: icon badge + title
            JPanel top = new JPanel(new BorderLayout(10, 0));
            top.setOpaque(false);
            JLabel icon = new JLabel((String) c[4]);
            icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20));
            icon.setOpaque(true);
            icon.setBackground((Color) c[2]);
            icon.setBorder(javax.swing.BorderFactory.createEmptyBorder(6,10,6,10));
            JLabel title = new JLabel((String) c[0]);
            title.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            title.setForeground(TEXT_GRAY);
            top.add(icon, BorderLayout.WEST);
            top.add(title, BorderLayout.CENTER);

            // Value
            JLabel val = new JLabel((String) c[1]);
            val.setFont(new Font("Segoe UI", Font.BOLD, 26));
            val.setForeground((Color) c[3]);
            valueLabels[i] = val;
            card.add(top, BorderLayout.NORTH);
            card.add(val, BorderLayout.CENTER);
            panelCards.add(card);
        }
        valDonHomNay   = valueLabels[0];
        valDoanhThu    = valueLabels[1];
        valSanHoatDong = valueLabels[2];
        valTongDon     = valueLabels[3];
    }

    private void buildGreeting() {
        if (currentUser != null) {
            lblGreeting.setText("Xin chào, " + currentUser.getFullName() + "! 👋");
        }
    }

    // ── Load data (gọi từ Dashboard khi chuyển panel) ───────────
    public void loadData() {
        new SwingWorker<Object[], Void>() {
            @Override protected Object[] doInBackground() throws Exception {
                LocalDate today = LocalDate.now();
                int donHN  = bookingDAO.countTodayBookings(today);
                BigDecimal dt = paymentDAO.getTodayRevenue(today);
                int san    = courtDAO.countActiveCourts();
                int total  = bookingDAO.countTotalBookings();
                List<Object[]> rows = bookingDAO.getTodayBookingRows(today);
                return new Object[]{donHN, dt, san, total, rows};
            }
            @SuppressWarnings("unchecked")
            @Override protected void done() {
                try {
                    Object[] r = get();
                    valDonHomNay  .setText(r[0].toString());
                    BigDecimal dt = (BigDecimal) r[1];
                    valDoanhThu   .setText(FMT.format(dt != null ? dt : BigDecimal.ZERO) + "đ");
                    valSanHoatDong.setText(r[2].toString());
                    valTongDon    .setText(r[3].toString());
                    // Table
                    DefaultTableModel m = (DefaultTableModel) tblBookings.getModel();
                    m.setRowCount(0);
                    for (Object[] row : (List<Object[]>) r[4]) {
                        m.addRow(new Object[]{
                            row[0], row[1], row[3],
                            row[4] + " – " + row[5],
                            formatStatus((String) row[6]),
                            FMT.format(row[7]) + " đ"
                        });
                    }
                    styleTable();
                    if (m.getRowCount() == 0)
                        m.addRow(new Object[]{"","Chưa có đơn nào hôm nay","","","",""});
                } catch(Exception ex) {
                    JOptionPane.showMessageDialog(PanelTongQuan.this,
                        "Lỗi tải dữ liệu: " + ex.getMessage());
                }
            }
        }.execute();
    }

    private void styleTable() {
        int[] w = {120,180,140,110,120,100};
        for (int i=0;i<w.length;i++)
            tblBookings.getColumnModel().getColumn(i).setPreferredWidth(w[i]);

        DefaultTableCellRenderer base = new DefaultTableCellRenderer() {
            @Override public Component getTableCellRendererComponent(JTable t,Object v,boolean sel,boolean foc,int r,int c) {
                super.getTableCellRendererComponent(t,v,sel,foc,r,c);
                setFont(new Font("Segoe UI",Font.PLAIN,13));
                setBorder(javax.swing.BorderFactory.createEmptyBorder(0,12,0,12));
                if(!sel) {
                    setBackground(r%2==0?Color.WHITE:new Color(250,251,252));
                    setForeground(TEXT_DARK);
                }
                return this;
            }
        };
        for (int i=0;i<4;i++) tblBookings.getColumnModel().getColumn(i).setCellRenderer(base);

        // Status col
        tblBookings.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {
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
                lbl.setBorder(javax.swing.BorderFactory.createEmptyBorder(4,10,4,10));
                JPanel wp=new JPanel(new FlowLayout(FlowLayout.CENTER,0,10));
                wp.setBackground(r%2==0?Color.WHITE:new Color(250,251,252));
                wp.add(lbl);return wp;
            }
        });
        // Amount col right-align
        DefaultTableCellRenderer right = new DefaultTableCellRenderer();
        right.setHorizontalAlignment(SwingConstants.RIGHT);
        tblBookings.getColumnModel().getColumn(5).setCellRenderer(right);
    }

    public static String formatStatus(String raw) {
        if (raw == null) return "";
        if ("CHO_THANH_TOAN".equals(raw)) return "Chờ thanh toán";
        if ("DA_THANH_TOAN" .equals(raw)) return "Đã thanh toán";
        if ("DA_HUY"        .equals(raw)) return "Đã hủy";
        if ("HOAN_THANH"    .equals(raw)) return "Hoàn thành";
        return raw;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel  lblGreeting;
    private javax.swing.JLabel  lblSectionTitle;
    private javax.swing.JLabel  lblSubtitle;
    private javax.swing.JPanel  panelCards;
    private javax.swing.JScrollPane scrollTable;
    private javax.swing.JTable  tblBookings;
    // End of variables declaration//GEN-END:variables
}
