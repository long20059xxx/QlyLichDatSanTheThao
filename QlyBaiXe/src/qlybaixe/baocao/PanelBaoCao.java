package qlybaixe.baocao;

import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import qlybaixe.dao.PaymentDAO;
import qlybaixe.dashboard.Dashboard;
import qlybaixe.model.User;

/**
 * Báo cáo doanh thu – chuẩn NetBeans JPanel Form
 */
public class PanelBaoCao extends javax.swing.JPanel {

    private static final Color PRIMARY    = Dashboard.PRIMARY;
    private static final Color BG         = new Color(245, 246, 250);
    private static final Color WHITE      = Color.WHITE;
    private static final Color TEXT_DARK  = Dashboard.TEXT_DARK;
    private static final Color TEXT_GRAY  = Dashboard.TEXT_GRAY;
    private static final Color BORDER_CLR = Dashboard.BORDER_CLR;

    private static final DecimalFormat     FMT = new DecimalFormat("#,###");
    private static final DateTimeFormatter DF  = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter DFD = DateTimeFormatter.ofPattern("EEE, dd/MM/yyyy",
                                                     new java.util.Locale("vi", "VN"));

    private final PaymentDAO paymentDAO = new PaymentDAO();
    private BigDecimal maxCourtRev = BigDecimal.ONE;

    // ── Tóm tắt quick (nằm ngoài panel chính để cập nhật) ───────
    private JLabel valTotalRev, valTotalOrders, valDayCount, valAvgPerDay;

    public PanelBaoCao(User user, Dashboard dash) {
        initComponents();
        setupUI();
        setRange(-29, 0);   // Mặc định 30 ngày
    }
    public PanelBaoCao() { this(null, null); }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    @SuppressWarnings("unchecked")
    private void initComponents() {

        scrollRoot     = new javax.swing.JScrollPane();
        panelRoot      = new javax.swing.JPanel();
        panelTop       = new javax.swing.JPanel();
        lblTitle       = new javax.swing.JLabel();
        lblSubtitle    = new javax.swing.JLabel();
        panelFilter    = new javax.swing.JPanel();
        btnToday       = new javax.swing.JButton();
        btnWeek        = new javax.swing.JButton();
        btnMonth       = new javax.swing.JButton();
        btnQuarter     = new javax.swing.JButton();
        spnFrom        = new javax.swing.JSpinner();
        spnTo          = new javax.swing.JSpinner();
        btnLoad        = new javax.swing.JButton();
        panelCards     = new javax.swing.JPanel();
        panelCharts    = new javax.swing.JPanel();
        panelByDay     = new javax.swing.JPanel();
        scrollByDay    = new javax.swing.JScrollPane();
        tblByDay       = new javax.swing.JTable();
        panelByCourt   = new javax.swing.JPanel();
        scrollByCourt  = new javax.swing.JScrollPane();
        tblByCourt     = new javax.swing.JTable();

        setLayout(new java.awt.BorderLayout());
        setBackground(BG);
    }// </editor-fold>//GEN-END:initComponents

    private void setupUI() {
        // ═══════════════════════════════════════════════════════
        // ROOT scroll
        // ═══════════════════════════════════════════════════════
        scrollRoot.setBorder(null);
        scrollRoot.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollRoot.getVerticalScrollBar().setUnitIncrement(16);
        scrollRoot.getViewport().setBackground(BG);

        panelRoot.setBackground(BG);
        panelRoot.setLayout(new BorderLayout());
        scrollRoot.setViewportView(panelRoot);
        add(scrollRoot, BorderLayout.CENTER);

        // ═══════════════════════════════════════════════════════
        // TOP: title + filter bar
        // ═══════════════════════════════════════════════════════
        panelTop.setBackground(BG);
        panelTop.setBorder(new EmptyBorder(24, 28, 0, 28));
        panelTop.setLayout(new BoxLayout(panelTop, BoxLayout.Y_AXIS));
        panelRoot.add(panelTop, BorderLayout.NORTH);

        // Title row
        lblTitle.setText("Báo cáo doanh thu");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(TEXT_DARK);
        lblTitle.setAlignmentX(LEFT_ALIGNMENT);

        lblSubtitle.setText("Thống kê doanh thu và hiệu suất kinh doanh theo kỳ");
        lblSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblSubtitle.setForeground(TEXT_GRAY);
        lblSubtitle.setAlignmentX(LEFT_ALIGNMENT);

        panelTop.add(lblTitle);
        panelTop.add(Box.createVerticalStrut(3));
        panelTop.add(lblSubtitle);
        panelTop.add(Box.createVerticalStrut(16));

        // Filter bar
        panelFilter.setBackground(WHITE);
        panelFilter.setBorder(new CompoundBorder(
            new LineBorder(BORDER_CLR, 1, true),
            new EmptyBorder(12, 20, 12, 20)));
        panelFilter.setLayout(new FlowLayout(FlowLayout.LEFT, 8, 0));
        panelFilter.setAlignmentX(LEFT_ALIGNMENT);
        panelFilter.setMaximumSize(new Dimension(Integer.MAX_VALUE, 62));

        // Quick btns
        for (JButton b : new JButton[]{btnToday, btnWeek, btnMonth, btnQuarter}) {
            b.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            b.setBackground(new Color(241, 245, 249));
            b.setForeground(TEXT_DARK);
            b.setBorder(new LineBorder(BORDER_CLR, 1, true));
            b.setFocusPainted(false);
            b.setCursor(new Cursor(Cursor.HAND_CURSOR));
            b.setPreferredSize(new Dimension(95, 34));
            panelFilter.add(b);
        }
        btnToday  .setText("Hôm nay");
        btnWeek   .setText("Tuần này");
        btnMonth  .setText("Tháng này");
        btnQuarter.setText("Quý này");

        btnToday  .addActionListener(e -> { highlightQBtn(btnToday);   setRange(0, 0);    });
        btnWeek   .addActionListener(e -> { highlightQBtn(btnWeek);    setRange(-6, 0);   });
        btnMonth  .addActionListener(e -> { highlightQBtn(btnMonth);   setRange(-29, 0);  });
        btnQuarter.addActionListener(e -> { highlightQBtn(btnQuarter); setRange(-89, 0);  });

        // Separator
        JLabel sep = new JLabel("  |  "); sep.setForeground(BORDER_CLR); panelFilter.add(sep);

        // Date spinners
        JLabel lFrom = new JLabel("Từ"); lFrom.setFont(new Font("Segoe UI",Font.PLAIN,12)); lFrom.setForeground(TEXT_GRAY);
        JLabel lTo   = new JLabel("đến"); lTo.setFont(new Font("Segoe UI",Font.PLAIN,12)); lTo.setForeground(TEXT_GRAY);

        Calendar cFrom = Calendar.getInstance(); cFrom.add(Calendar.DAY_OF_MONTH, -29);
        spnFrom.setModel(new SpinnerDateModel(cFrom.getTime(), null, null, Calendar.DAY_OF_MONTH));
        JSpinner.DateEditor deFrom = new JSpinner.DateEditor(spnFrom, "dd/MM/yyyy");
        spnFrom.setEditor(deFrom); spnFrom.setPreferredSize(new Dimension(120, 34));
        ((JSpinner.DefaultEditor)spnFrom.getEditor()).getTextField().setFont(new Font("Segoe UI",Font.PLAIN,12));

        spnTo.setModel(new SpinnerDateModel());
        JSpinner.DateEditor deTo = new JSpinner.DateEditor(spnTo, "dd/MM/yyyy");
        spnTo.setEditor(deTo); spnTo.setPreferredSize(new Dimension(120, 34));
        ((JSpinner.DefaultEditor)spnTo.getEditor()).getTextField().setFont(new Font("Segoe UI",Font.PLAIN,12));

        btnLoad.setText("📊  Xem báo cáo");
        btnLoad.setFont(new Font("Segoe UI",Font.BOLD,13));
        btnLoad.setBackground(PRIMARY); btnLoad.setForeground(WHITE);
        btnLoad.setBorderPainted(false); btnLoad.setFocusPainted(false);
        btnLoad.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLoad.setPreferredSize(new Dimension(150, 34));
        btnLoad.addActionListener(e -> loadReport());

        panelFilter.add(lFrom); panelFilter.add(spnFrom);
        panelFilter.add(lTo);   panelFilter.add(spnTo);
        panelFilter.add(Box.createHorizontalStrut(6));
        panelFilter.add(btnLoad);

        panelTop.add(panelFilter);

        // ═══════════════════════════════════════════════════════
        // CENTER area
        // ═══════════════════════════════════════════════════════
        JPanel centerWrap = new JPanel();
        centerWrap.setBackground(BG);
        centerWrap.setLayout(new BoxLayout(centerWrap, BoxLayout.Y_AXIS));
        centerWrap.setBorder(new EmptyBorder(16, 28, 28, 28));
        panelRoot.add(centerWrap, BorderLayout.CENTER);

        // Stat cards
        panelCards.setBackground(BG);
        panelCards.setLayout(new GridLayout(1, 4, 14, 0));
        panelCards.setMaximumSize(new Dimension(Integer.MAX_VALUE, 110));
        panelCards.setAlignmentX(LEFT_ALIGNMENT);
        buildCards("—", "—", "—", "—");   // placeholder
        centerWrap.add(panelCards);
        centerWrap.add(Box.createVerticalStrut(16));

        // Charts row
        panelCharts.setBackground(BG);
        panelCharts.setLayout(new GridLayout(1, 2, 16, 0));
        panelCharts.setAlignmentX(LEFT_ALIGNMENT);
        panelCharts.setPreferredSize(new Dimension(Integer.MAX_VALUE, 380));

        buildByDayPanel();
        buildByCourtPanel();
        panelCharts.add(panelByDay);
        panelCharts.add(panelByCourt);
        centerWrap.add(panelCharts);
    }

    // ── Stat cards ────────────────────────────────────────────
    private static final Object[][] CARD_META = {
        {"Tổng doanh thu",   "💰", new Color(219,234,254), new Color(29,78,216)},
        {"Đơn đã thanh toán","📋", new Color(220,252,231), new Color(22,163,74)},
        {"Ngày có doanh thu","📅", new Color(237,233,254), new Color(124,58,237)},
        {"TB doanh thu/ngày","📈", new Color(254,235,200), new Color(194,65,12)},
    };

    private void buildCards(String v1,String v2,String v3,String v4){
        panelCards.removeAll();
        String[] vals={v1,v2,v3,v4};
        for(int i=0;i<4;i++){
            Object[] m=CARD_META[i];
            JPanel card=new JPanel(new BorderLayout(0,8));
            card.setBackground(WHITE);
            card.setBorder(new CompoundBorder(new LineBorder(BORDER_CLR,1,true),new EmptyBorder(16,18,16,18)));
            // Top row: icon + label
            JPanel top=new JPanel(new BorderLayout(10,0)); top.setOpaque(false);
            JLabel icn=new JLabel((String)m[1]);
            icn.setFont(new Font("Segoe UI Emoji",Font.PLAIN,22));
            icn.setOpaque(true); icn.setBackground((Color)m[2]);
            icn.setBorder(new EmptyBorder(8,12,8,12));
            JLabel lbl=new JLabel((String)m[0]);
            lbl.setFont(new Font("Segoe UI",Font.PLAIN,12)); lbl.setForeground(TEXT_GRAY);
            top.add(icn,BorderLayout.WEST); top.add(lbl,BorderLayout.CENTER);
            // Value
            JLabel val=new JLabel(vals[i]);
            val.setFont(new Font("Segoe UI",Font.BOLD,20)); val.setForeground((Color)m[3]);
            card.add(top,BorderLayout.NORTH); card.add(val,BorderLayout.CENTER);
            panelCards.add(card);
        }
        panelCards.revalidate(); panelCards.repaint();
    }

    // ── Bảng doanh thu theo ngày ──────────────────────────────
    private void buildByDayPanel(){
        panelByDay.setBackground(WHITE);
        panelByDay.setBorder(new LineBorder(BORDER_CLR,1,true));
        panelByDay.setLayout(new BorderLayout());

        JPanel hdr=new JPanel(new BorderLayout()); hdr.setBackground(WHITE);
        hdr.setBorder(new EmptyBorder(16,18,12,18));
        JLabel t=new JLabel("Doanh thu theo ngày");
        t.setFont(new Font("Segoe UI",Font.BOLD,14)); t.setForeground(TEXT_DARK);
        JLabel sub=new JLabel("Chi tiết từng ngày");
        sub.setFont(new Font("Segoe UI",Font.PLAIN,11)); sub.setForeground(TEXT_GRAY);
        hdr.add(t,BorderLayout.NORTH); hdr.add(sub,BorderLayout.CENTER);

        tblByDay.setModel(new DefaultTableModel(new Object[][]{},
            new String[]{"NGÀY","SỐ ĐƠN","DOANH THU","TĂNG TRƯỞNG"}){
            @Override public boolean isCellEditable(int r,int c){return false;}
        });
        styleTable(tblByDay);
        tblByDay.getColumnModel().getColumn(0).setPreferredWidth(130);
        tblByDay.getColumnModel().getColumn(1).setPreferredWidth(70);
        tblByDay.getColumnModel().getColumn(2).setPreferredWidth(120);
        tblByDay.getColumnModel().getColumn(3).setPreferredWidth(90);

        // Renderer cột tăng trưởng
        tblByDay.getColumnModel().getColumn(3).setCellRenderer((t2,v,sel,foc,r,c)->{
            String s=v==null?"":v.toString();
            boolean up=s.startsWith("+");
            JLabel l=new JLabel(s,SwingConstants.CENTER);
            l.setFont(new Font("Segoe UI",Font.BOLD,11));
            l.setForeground(up?new Color(22,163,74):s.startsWith("-")?new Color(220,38,38):TEXT_GRAY);
            l.setBackground(r%2==0?WHITE:new Color(250,251,252));
            l.setOpaque(true); l.setBorder(new EmptyBorder(0,6,0,6));
            return l;
        });

        scrollByDay.setViewportView(tblByDay);
        scrollByDay.setBorder(null);
        scrollByDay.getViewport().setBackground(WHITE);

        panelByDay.add(hdr,BorderLayout.NORTH);
        panelByDay.add(scrollByDay,BorderLayout.CENTER);
    }

    // ── Bảng doanh thu theo sân ───────────────────────────────
    private void buildByCourtPanel(){
        panelByCourt.setBackground(WHITE);
        panelByCourt.setBorder(new LineBorder(BORDER_CLR,1,true));
        panelByCourt.setLayout(new BorderLayout());

        JPanel hdr=new JPanel(new BorderLayout()); hdr.setBackground(WHITE);
        hdr.setBorder(new EmptyBorder(16,18,12,18));
        JLabel t=new JLabel("Doanh thu theo sân");
        t.setFont(new Font("Segoe UI",Font.BOLD,14)); t.setForeground(TEXT_DARK);
        JLabel sub=new JLabel("Hiệu suất từng sân trong kỳ");
        sub.setFont(new Font("Segoe UI",Font.PLAIN,11)); sub.setForeground(TEXT_GRAY);
        hdr.add(t,BorderLayout.NORTH); hdr.add(sub,BorderLayout.CENTER);

        tblByCourt.setModel(new DefaultTableModel(new Object[][]{},
            new String[]{"SÂN","LOẠI","ĐƠN","DOANH THU","TỈ TRỌNG"}){
            @Override public boolean isCellEditable(int r,int c){return false;}
        });
        styleTable(tblByCourt);
        tblByCourt.getColumnModel().getColumn(0).setPreferredWidth(150);
        tblByCourt.getColumnModel().getColumn(1).setPreferredWidth(80);
        tblByCourt.getColumnModel().getColumn(2).setPreferredWidth(50);
        tblByCourt.getColumnModel().getColumn(3).setPreferredWidth(110);
        tblByCourt.getColumnModel().getColumn(4).setPreferredWidth(110);

        // Renderer cột tỉ trọng (progress bar)
        tblByCourt.getColumnModel().getColumn(4).setCellRenderer((t2,v,sel,foc,r,c)->{
            double pct=0;
            if(v instanceof Double) pct=(Double)v;
            else if(v instanceof BigDecimal) pct=((BigDecimal)v).doubleValue();
            JPanel wp=new JPanel(new BorderLayout(4,0)); wp.setOpaque(true);
            wp.setBackground(r%2==0?WHITE:new Color(250,251,252));
            wp.setBorder(new EmptyBorder(8,10,8,10));
            JPanel track=new JPanel(null); track.setBackground(new Color(229,231,235)); track.setOpaque(true);
            track.setPreferredSize(new Dimension(140,8));
            JPanel fill=new JPanel(); fill.setBackground(PRIMARY); fill.setOpaque(true);
            fill.setBounds(0,0,(int)(pct/100*140),8);
            track.add(fill);
            JLabel txt=new JLabel(String.format("%.1f%%",pct)); txt.setFont(new Font("Segoe UI",Font.BOLD,11)); txt.setForeground(TEXT_DARK);
            wp.add(txt,BorderLayout.WEST); wp.add(track,BorderLayout.CENTER);
            return wp;
        });

        scrollByCourt.setViewportView(tblByCourt);
        scrollByCourt.setBorder(null);
        scrollByCourt.getViewport().setBackground(WHITE);

        panelByCourt.add(hdr,BorderLayout.NORTH);
        panelByCourt.add(scrollByCourt,BorderLayout.CENTER);
    }

    // ── Util: style table chung ───────────────────────────────
    private void styleTable(JTable t){
        t.setRowHeight(42); t.setFont(new Font("Segoe UI",Font.PLAIN,13));
        t.setShowGrid(false); t.setIntercellSpacing(new Dimension(0,0));
        t.setSelectionBackground(new Color(235,240,255));
        t.getTableHeader().setFont(new Font("Segoe UI",Font.BOLD,10));
        t.getTableHeader().setBackground(new Color(249,250,251));
        t.getTableHeader().setForeground(TEXT_GRAY);
        t.getTableHeader().setPreferredSize(new Dimension(0,36));
        t.getTableHeader().setBorder(new MatteBorder(0,0,1,0,BORDER_CLR));

        DefaultTableCellRenderer base=new DefaultTableCellRenderer(){
            @Override public Component getTableCellRendererComponent(JTable t2,Object v,boolean sel,boolean foc,int r,int c){
                super.getTableCellRendererComponent(t2,v,sel,foc,r,c);
                setFont(new Font("Segoe UI",Font.PLAIN,13));
                setBorder(new EmptyBorder(0,12,0,12));
                if(!sel){setBackground(r%2==0?WHITE:new Color(250,251,252));setForeground(TEXT_DARK);}
                return this;
            }
        };
        DefaultTableCellRenderer right=new DefaultTableCellRenderer(){
            @Override public Component getTableCellRendererComponent(JTable t2,Object v,boolean sel,boolean foc,int r,int c){
                super.getTableCellRendererComponent(t2,v,sel,foc,r,c);
                setFont(new Font("Segoe UI",Font.PLAIN,13));
                setBorder(new EmptyBorder(0,12,0,12)); setHorizontalAlignment(RIGHT);
                if(!sel){setBackground(r%2==0?WHITE:new Color(250,251,252));setForeground(TEXT_DARK);}
                return this;
            }
        };
        for(int i=0;i<t.getColumnCount()-1;i++) t.getColumnModel().getColumn(i).setCellRenderer(base);
    }

    // ── Quick btn highlight ───────────────────────────────────
    private JButton activeQBtn = null;
    private void highlightQBtn(JButton b){
        if(activeQBtn!=null){ activeQBtn.setBackground(new Color(241,245,249)); activeQBtn.setForeground(TEXT_DARK); }
        activeQBtn=b; b.setBackground(PRIMARY); b.setForeground(WHITE);
    }

    // ── Set date range ────────────────────────────────────────
    private void setRange(int fromOff,int toOff){
        Calendar f=Calendar.getInstance(); f.add(Calendar.DAY_OF_MONTH,fromOff);
        Calendar t=Calendar.getInstance(); t.add(Calendar.DAY_OF_MONTH,toOff);
        spnFrom.setValue(f.getTime()); spnTo.setValue(t.getTime());
        loadReport();
    }

    /** Gọi khi panel được hiển thị */
    public void loadData(){ loadReport(); }

    // ── Load & render ─────────────────────────────────────────
    private void loadReport(){
        java.util.Date dFrom=(java.util.Date)spnFrom.getValue();
        java.util.Date dTo  =(java.util.Date)spnTo.getValue();
        LocalDate from=dFrom.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        LocalDate to  =dTo.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        btnLoad.setEnabled(false); btnLoad.setText("Đang tải...");

        new SwingWorker<Object[],Void>(){
            @Override protected Object[] doInBackground() throws Exception {
                Object[]       sum     = paymentDAO.getSummary(from, to);
                List<Object[]> byDay   = paymentDAO.getRevenueByDay(from, to);
                List<Object[]> byCourt = paymentDAO.getRevenueByCourt(from, to);
                return new Object[]{sum, byDay, byCourt};
            }
            @SuppressWarnings("unchecked")
            @Override protected void done(){
                btnLoad.setEnabled(true); btnLoad.setText("📊  Xem báo cáo");
                try{
                    Object[]       data    =(Object[])get();
                    Object[]       sum     =(Object[])data[0];
                    List<Object[]> byDay   =(List<Object[]>)data[1];
                    List<Object[]> byCourt =(List<Object[]>)data[2];

                    BigDecimal totalRev=(BigDecimal)sum[0];
                    int totalOrders=(int)sum[1], paidOrders=(int)sum[2];
                    int days=byDay.isEmpty()?1:byDay.size();
                    BigDecimal avg=totalRev.compareTo(BigDecimal.ZERO)==0?BigDecimal.ZERO
                        :totalRev.divide(BigDecimal.valueOf(days),0,RoundingMode.HALF_UP);

                    buildCards(FMT.format(totalRev)+" đ",
                               String.valueOf(paidOrders),
                               String.valueOf(days),
                               FMT.format(avg)+" đ");

                    // Table by day
                    DefaultTableModel md=(DefaultTableModel)tblByDay.getModel(); md.setRowCount(0);
                    BigDecimal prev=null;
                    for(Object[] r:byDay){
                        LocalDate d=(LocalDate)r[0]; BigDecimal rev=(BigDecimal)r[1]; int cnt=(int)r[2];
                        String growth="—";
                        if(prev!=null&&prev.compareTo(BigDecimal.ZERO)!=0){
                            BigDecimal pct=rev.subtract(prev).multiply(BigDecimal.valueOf(100))
                                           .divide(prev,1,RoundingMode.HALF_UP);
                            growth=(pct.compareTo(BigDecimal.ZERO)>=0?"+":"")+pct+"%";
                        }
                        md.addRow(new Object[]{d.format(DFD), cnt+" đơn", FMT.format(rev)+" đ", growth});
                        prev=rev;
                    }
                    if(md.getRowCount()==0) md.addRow(new Object[]{"Chưa có dữ liệu","","",""});

                    // Table by court
                    BigDecimal totalForPct=totalRev.compareTo(BigDecimal.ZERO)==0?BigDecimal.ONE:totalRev;
                    DefaultTableModel mc=(DefaultTableModel)tblByCourt.getModel(); mc.setRowCount(0);
                    for(Object[] r:byCourt){
                        String nm=(String)r[0],tp=fmtType((String)r[1]);
                        BigDecimal rev=(BigDecimal)r[2]; int cnt=(int)r[3];
                        double pct=rev.multiply(BigDecimal.valueOf(100))
                                      .divide(totalForPct,2,RoundingMode.HALF_UP).doubleValue();
                        mc.addRow(new Object[]{nm, tp, cnt, FMT.format(rev)+" đ", pct});
                    }
                    if(mc.getRowCount()==0) mc.addRow(new Object[]{"Chưa có dữ liệu","","","",0.0});
                }catch(Exception ex){
                    JOptionPane.showMessageDialog(PanelBaoCao.this,"Lỗi: "+ex.getMessage());
                }
            }
        }.execute();
    }

    private String fmtType(String t){
        if(t==null) return "";
        switch(t){ case "BONG_DA":return"Bóng Đá"; case "CAU_LONG":return"Cầu Lông";
                   case "TENNIS": return"Tennis";   case "BONG_RO": return"Bóng Rổ"; default:return t; }
    }

    // Variables declaration//GEN-BEGIN:variables
    private javax.swing.JButton btnLoad, btnToday, btnWeek, btnMonth, btnQuarter;
    private javax.swing.JLabel  lblSubtitle, lblTitle;
    private javax.swing.JPanel  panelByDay, panelByCourt, panelCards, panelCharts, panelFilter, panelRoot, panelTop;
    private javax.swing.JScrollPane scrollByCourt, scrollByDay, scrollRoot;
    private javax.swing.JSpinner spnFrom, spnTo;
    private javax.swing.JTable  tblByCourt, tblByDay;
    // End of variables declaration//GEN-END:variables
}
