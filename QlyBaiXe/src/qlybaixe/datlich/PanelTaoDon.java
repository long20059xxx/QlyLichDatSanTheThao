package qlybaixe.datlich;

import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.*;
import qlybaixe.dao.BookingDAO;
import qlybaixe.dao.CourtDAO;
import qlybaixe.dashboard.Dashboard;
import qlybaixe.model.Booking;
import qlybaixe.model.Court;
import qlybaixe.model.User;


/**
 * JPanel – form "Tạo đơn đặt sân mới" nhúng vào Dashboard.
 * Chuẩn NetBeans JPanel Form.
 */
public class PanelTaoDon extends javax.swing.JPanel {

    private static final Color PRIMARY    = Dashboard.PRIMARY;
    private static final Color PRIMARY_LT = new Color(239, 246, 255);
    private static final Color TEXT_DARK  = Dashboard.TEXT_DARK;
    private static final Color TEXT_GRAY  = Dashboard.TEXT_GRAY;
    private static final Color BORDER     = Dashboard.BORDER_CLR;

    private static final DecimalFormat FMT = new DecimalFormat("#,###");

    private final BookingDAO bookingDAO = new BookingDAO();
    private final CourtDAO   courtDAO   = new CourtDAO();
    private final User       currentUser;
    private final Dashboard  dashboard;
    private List<Court>      courts;

    // Summary labels (built in buildSummary)
    private JLabel valSan, valThoiGian, valGia, valTong;

    public PanelTaoDon(User user, Dashboard dash) {
        this.currentUser = user;
        this.dashboard   = dash;
        initComponents();
        setupSpinners();
        buildSummary();
        loadCourts();
    }

    public PanelTaoDon() { this(null, null); }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollOuter = new javax.swing.JScrollPane();
        panelOuter = new javax.swing.JPanel();
        btnBack = new javax.swing.JButton();
        lblTitle = new javax.swing.JLabel();
        lblSubtitle = new javax.swing.JLabel();
        panelForm = new javax.swing.JPanel();
        lblCourtLabel = new javax.swing.JLabel();
        cmbCourt = new javax.swing.JComboBox();
        lblCustName = new javax.swing.JLabel();
        txtCustName = new javax.swing.JTextField();
        lblCustPhone = new javax.swing.JLabel();
        txtCustPhone = new javax.swing.JTextField();
        lblDateLabel = new javax.swing.JLabel();
        spnDate = new javax.swing.JSpinner();
        lblTimeLabel = new javax.swing.JLabel();
        spnStart = new javax.swing.JSpinner();
        lblTimeSep = new javax.swing.JLabel();
        spnEnd = new javax.swing.JSpinner();
        lblStartHint = new javax.swing.JLabel();
        lblEndHint = new javax.swing.JLabel();
        lblNotesLabel = new javax.swing.JLabel();
        scrNotes = new javax.swing.JScrollPane();
        txaNotes = new javax.swing.JTextArea();
        panelSummary = new javax.swing.JPanel();
        panelButtons = new javax.swing.JPanel();
        btnCreate = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        panelOuter.setBackground(new java.awt.Color(245, 246, 250));

        btnBack.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        btnBack.setText("←");
        btnBack.setBorderPainted(false);
        btnBack.setContentAreaFilled(false);
        btnBack.setFocusPainted(false);

        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(30, 41, 59));
        lblTitle.setText("Tạo đơn đặt sân mới");

        lblSubtitle.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblSubtitle.setForeground(new java.awt.Color(107, 114, 128));
        lblSubtitle.setText("Nhập thông tin khách hàng và lịch đặt sân");

        panelForm.setBackground(new java.awt.Color(255, 255, 255));

        lblCourtLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblCourtLabel.setText("Chọn sân *");

        cmbCourt.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        lblCustName.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblCustName.setText("Tên khách hàng *");

        txtCustName.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        lblCustPhone.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblCustPhone.setText("Số điện thoại *");

        txtCustPhone.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        lblDateLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblDateLabel.setText("Ngày chơi *");

        spnDate.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        lblTimeLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblTimeLabel.setText("Thời gian *");

        spnStart.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        lblTimeSep.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblTimeSep.setText("–");

        spnEnd.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        lblStartHint.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        lblStartHint.setForeground(new java.awt.Color(107, 114, 128));
        lblStartHint.setText("Bắt đầu");

        lblEndHint.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        lblEndHint.setForeground(new java.awt.Color(107, 114, 128));
        lblEndHint.setText("Kết thúc");

        lblNotesLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblNotesLabel.setText("Ghi chú");

        txaNotes.setRows(4);
        txaNotes.setLineWrap(true);
        txaNotes.setWrapStyleWord(true);
        scrNotes.setViewportView(txaNotes);

        panelSummary.setBackground(new java.awt.Color(239, 246, 255));

        javax.swing.GroupLayout panelSummaryLayout = new javax.swing.GroupLayout(panelSummary);
        panelSummary.setLayout(panelSummaryLayout);
        panelSummaryLayout.setHorizontalGroup(
            panelSummaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelSummaryLayout.setVerticalGroup(
            panelSummaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(140, 140, 140)
        );

        javax.swing.GroupLayout panelFormLayout = new javax.swing.GroupLayout(panelForm);
        panelForm.setLayout(panelFormLayout);
        panelFormLayout.setHorizontalGroup(
            panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFormLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCourtLabel)
                    .addComponent(cmbCourt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelFormLayout.createSequentialGroup()
                        .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCustName)
                            .addComponent(txtCustName, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCustPhone)
                            .addComponent(txtCustPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelFormLayout.createSequentialGroup()
                        .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDateLabel)
                            .addComponent(spnDate, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTimeLabel)
                            .addGroup(panelFormLayout.createSequentialGroup()
                                .addComponent(spnStart, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(8, 8, 8)
                                .addComponent(lblTimeSep)
                                .addGap(8, 8, 8)
                                .addComponent(spnEnd, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelFormLayout.createSequentialGroup()
                                .addComponent(lblStartHint, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(24, 24, 24)
                                .addComponent(lblEndHint))))
                    .addComponent(lblNotesLabel)
                    .addComponent(scrNotes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelSummary, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(24, 24, 24))
        );
        panelFormLayout.setVerticalGroup(
            panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFormLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lblCourtLabel)
                .addGap(4, 4, 4)
                .addComponent(cmbCourt, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCustName)
                    .addComponent(lblCustPhone))
                .addGap(4, 4, 4)
                .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCustName, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCustPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDateLabel)
                    .addComponent(lblTimeLabel))
                .addGap(4, 4, 4)
                .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spnDate, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spnStart, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTimeSep)
                    .addComponent(spnEnd, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblStartHint)
                    .addComponent(lblEndHint))
                .addGap(16, 16, 16)
                .addComponent(lblNotesLabel)
                .addGap(4, 4, 4)
                .addComponent(scrNotes, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addComponent(panelSummary, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        panelButtons.setOpaque(false);

        btnCreate.setBackground(new java.awt.Color(13, 71, 161));
        btnCreate.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnCreate.setForeground(new java.awt.Color(255, 255, 255));
        btnCreate.setText("📋  Tạo đơn");
        btnCreate.setBorderPainted(false);
        btnCreate.setFocusPainted(false);

        btnCancel.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnCancel.setText("Hủy");
        btnCancel.setBorderPainted(false);
        btnCancel.setFocusPainted(false);

        javax.swing.GroupLayout panelButtonsLayout = new javax.swing.GroupLayout(panelButtons);
        panelButtons.setLayout(panelButtonsLayout);
        panelButtonsLayout.setHorizontalGroup(
            panelButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelButtonsLayout.createSequentialGroup()
                .addComponent(btnCreate, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelButtonsLayout.setVerticalGroup(
            panelButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(btnCreate, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout panelOuterLayout = new javax.swing.GroupLayout(panelOuter);
        panelOuter.setLayout(panelOuterLayout);
        panelOuterLayout.setHorizontalGroup(
            panelOuterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOuterLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(panelOuterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelOuterLayout.createSequentialGroup()
                        .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addGroup(panelOuterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTitle)
                            .addComponent(lblSubtitle)))
                    .addComponent(panelForm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelButtons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24))
        );
        panelOuterLayout.setVerticalGroup(
            panelOuterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOuterLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(panelOuterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btnBack)
                    .addComponent(lblTitle))
                .addGap(2, 2, 2)
                .addComponent(lblSubtitle)
                .addGap(16, 16, 16)
                .addComponent(panelForm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addComponent(panelButtons, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );

        scrollOuter.setViewportView(panelOuter);

        add(scrollOuter, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    /** Setup DateEditors and action listeners (outside GEN so NetBeans won't overwrite). */
    private void setupSpinners() {
        // Date spinner
        spnDate.setModel(new SpinnerDateModel());
        JSpinner.DateEditor de = new JSpinner.DateEditor(spnDate, "dd/MM/yyyy");
        spnDate.setEditor(de);
        ((JSpinner.DefaultEditor) spnDate.getEditor()).getTextField().setFont(new Font("Segoe UI", Font.PLAIN, 13));

        // Start time spinner
        Calendar cs = Calendar.getInstance(); cs.set(Calendar.HOUR_OF_DAY, 8); cs.set(Calendar.MINUTE, 0); cs.set(Calendar.SECOND, 0);
        spnStart.setModel(new SpinnerDateModel(cs.getTime(), null, null, Calendar.MINUTE));
        JSpinner.DateEditor se = new JSpinner.DateEditor(spnStart, "HH:mm");
        spnStart.setEditor(se);
        ((JSpinner.DefaultEditor) spnStart.getEditor()).getTextField().setFont(new Font("Segoe UI", Font.PLAIN, 13));
        spnStart.addChangeListener(e -> updateSummary());

        // End time spinner
        Calendar ce = Calendar.getInstance(); ce.set(Calendar.HOUR_OF_DAY, 10); ce.set(Calendar.MINUTE, 0); ce.set(Calendar.SECOND, 0);
        spnEnd.setModel(new SpinnerDateModel(ce.getTime(), null, null, Calendar.MINUTE));
        JSpinner.DateEditor ee = new JSpinner.DateEditor(spnEnd, "HH:mm");
        spnEnd.setEditor(ee);
        ((JSpinner.DefaultEditor) spnEnd.getEditor()).getTextField().setFont(new Font("Segoe UI", Font.PLAIN, 13));
        spnEnd.addChangeListener(e -> updateSummary());

        // Action listeners
        btnBack  .addActionListener(e -> doBack());
        btnCreate.addActionListener(e -> doCreate());
        btnCancel.addActionListener(e -> doBack());
        cmbCourt .addActionListener(e -> updateSummary());

        // Cursor
        btnCreate.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCreate.setBackground(PRIMARY);
        btnCancel.setBackground(new Color(241, 245, 249));
    }

    private void buildSummary() {
        panelSummary.removeAll();
        GridBagConstraints gL=new GridBagConstraints(); gL.gridx=0; gL.anchor=GridBagConstraints.WEST; gL.insets=new Insets(2,0,2,0); gL.weightx=0.5;
        GridBagConstraints gR=new GridBagConstraints(); gR.gridx=1; gR.anchor=GridBagConstraints.EAST; gR.insets=new Insets(2,0,2,0); gR.weightx=0.5; gR.fill=GridBagConstraints.HORIZONTAL;

        JLabel hdr=new JLabel("Tóm tắt đơn hàng");
        hdr.setFont(new Font("Segoe UI",Font.BOLD,13)); hdr.setForeground(TEXT_DARK);
        GridBagConstraints hg=new GridBagConstraints(); hg.gridx=0; hg.gridy=0; hg.gridwidth=2; hg.anchor=GridBagConstraints.WEST; hg.insets=new Insets(0,0,10,0);
        panelSummary.add(hdr, hg);

        valSan      = addRow(panelSummary,"Sân:",       "—",1,gL,gR);
        valThoiGian = addRow(panelSummary,"Thời gian:", "—",2,gL,gR);
        valGia      = addRow(panelSummary,"Giá/giờ:",   "—",3,gL,gR);

        JSeparator sep=new JSeparator(); sep.setForeground(new Color(191,219,254));
        GridBagConstraints sg=new GridBagConstraints(); sg.gridx=0; sg.gridy=4; sg.gridwidth=2; sg.fill=GridBagConstraints.HORIZONTAL; sg.insets=new Insets(6,0,6,0);
        panelSummary.add(sep,sg);

        JLabel lbTong=new JLabel("Tổng tiền:");
        lbTong.setFont(new Font("Segoe UI",Font.BOLD,13)); lbTong.setForeground(TEXT_DARK);
        GridBagConstraints gl5=new GridBagConstraints(); gl5.gridx=0; gl5.gridy=5; gl5.anchor=GridBagConstraints.WEST; gl5.insets=new Insets(2,0,2,0);
        panelSummary.add(lbTong,gl5);
        valTong=new JLabel("0 đ"); valTong.setFont(new Font("Segoe UI",Font.BOLD,18)); valTong.setForeground(PRIMARY); valTong.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gr5=new GridBagConstraints(); gr5.gridx=1; gr5.gridy=5; gr5.anchor=GridBagConstraints.EAST; gr5.fill=GridBagConstraints.HORIZONTAL; gr5.insets=new Insets(2,0,2,0);
        panelSummary.add(valTong,gr5);
    }

    private JLabel addRow(JPanel p,String lbTxt,String init,int y,GridBagConstraints gL,GridBagConstraints gR) {
        JLabel lb=new JLabel(lbTxt); lb.setFont(new Font("Segoe UI",Font.PLAIN,12)); lb.setForeground(TEXT_GRAY);
        GridBagConstraints cL=(GridBagConstraints)gL.clone(); cL.gridy=y; p.add(lb,cL);
        JLabel val=new JLabel(init); val.setFont(new Font("Segoe UI",Font.PLAIN,12)); val.setForeground(TEXT_DARK); val.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints cR=(GridBagConstraints)gR.clone(); cR.gridy=y; p.add(val,cR);
        return val;
    }

    private void loadCourts() {
        new SwingWorker<List<Court>,Void>(){
            @Override protected List<Court> doInBackground() throws Exception { return courtDAO.getAllActiveCourts(); }
            @Override protected void done(){
                try{
                    courts=get(); cmbCourt.removeAllItems();
                    for(Court c:courts) cmbCourt.addItem(c.getName()+" – "+FMT.format(c.getPricePerHour())+"đ/giờ");
                    updateSummary();
                }catch(Exception ex){JOptionPane.showMessageDialog(PanelTaoDon.this,"Lỗi tải sân: "+ex.getMessage());}
            }
        }.execute();
    }

    private void updateSummary() {
        int idx=cmbCourt.getSelectedIndex();
        if(courts==null||idx<0||idx>=courts.size()) return;
        Court c=courts.get(idx);
        valSan.setText(c.getName());
        valGia.setText(FMT.format(c.getPricePerHour())+"đ");
        try{
            Date ds=(Date)spnStart.getValue(), de=(Date)spnEnd.getValue();
            long dl=de.getTime()-ds.getTime();
            if(dl<=0){valThoiGian.setText("Không hợp lệ");valTong.setText("—");return;}
            double h=dl/3600000.0;
            valThoiGian.setText(h==Math.floor(h)?(int)h+" giờ":String.format("%.1f giờ",h));
            BigDecimal total=c.getPricePerHour().multiply(BigDecimal.valueOf(h)).setScale(0,RoundingMode.HALF_UP);
            valTong.setText(FMT.format(total)+"đ");
        }catch(Exception ex){valThoiGian.setText("—");valTong.setText("—");}
    }

    /** Reset form khi mở lại */
    public void reset() {
        txtCustName.setText(""); txtCustPhone.setText(""); txaNotes.setText("");
        cmbCourt.setSelectedIndex(0);
        Calendar cs=Calendar.getInstance(); cs.set(Calendar.HOUR_OF_DAY,8); cs.set(Calendar.MINUTE,0); cs.set(Calendar.SECOND,0);
        spnStart.setValue(cs.getTime());
        Calendar ce=Calendar.getInstance(); ce.set(Calendar.HOUR_OF_DAY,10); ce.set(Calendar.MINUTE,0); ce.set(Calendar.SECOND,0);
        spnEnd.setValue(ce.getTime());
        spnDate.setValue(new Date());
        updateSummary();
    }

    private void doCreate() {
        String nm=txtCustName.getText().trim(), ph=txtCustPhone.getText().trim();
        if(nm.isEmpty()){JOptionPane.showMessageDialog(this,"Vui lòng nhập tên khách hàng.","Lỗi",JOptionPane.WARNING_MESSAGE);txtCustName.requestFocus();return;}
        if(ph.isEmpty()){JOptionPane.showMessageDialog(this,"Vui lòng nhập số điện thoại.","Lỗi",JOptionPane.WARNING_MESSAGE);txtCustPhone.requestFocus();return;}
        int idx=cmbCourt.getSelectedIndex();
        if(courts==null||idx<0){JOptionPane.showMessageDialog(this,"Vui lòng chọn sân.","Lỗi",JOptionPane.WARNING_MESSAGE);return;}
        Date dDate=(Date)spnDate.getValue(), dS=(Date)spnStart.getValue(), dE=(Date)spnEnd.getValue();
        if(dE.getTime()-dS.getTime()<=0){JOptionPane.showMessageDialog(this,"Giờ kết thúc phải sau giờ bắt đầu.","Lỗi",JOptionPane.WARNING_MESSAGE);return;}
        Court court=courts.get(idx);
        LocalDate  bd=dDate.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        LocalTime  st=dS.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalTime().withSecond(0).withNano(0);
        LocalTime  et=dE.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalTime().withSecond(0).withNano(0);
        double h=(dE.getTime()-dS.getTime())/3600000.0;
        BigDecimal dur=BigDecimal.valueOf(h).setScale(1,RoundingMode.HALF_UP);
        BigDecimal tot=court.getPricePerHour().multiply(dur).setScale(0,RoundingMode.HALF_UP);

        btnCreate.setEnabled(false); ((JButton)btnCreate).setText("Đang xử lý...");
        new SwingWorker<String,Void>(){
            @Override protected String doInBackground() throws Exception {
                if(bookingDAO.hasConflict(court.getId(),bd,st,et,null)) return "CONFLICT";
                String code=bookingDAO.generateBookingCode(bd);
                Booking b=new Booking();
                b.setBookingCode(code); b.setCourtId(court.getId());
                b.setCustomerName(nm); b.setCustomerPhone(ph);
                b.setBookingDate(bd); b.setStartTime(st); b.setEndTime(et);
                b.setDurationHours(dur); b.setPricePerHour(court.getPricePerHour()); b.setTotalAmount(tot);
                b.setNotes(txaNotes.getText().trim());
                b.setCreatedBy(currentUser!=null&&currentUser.getId()!=null?currentUser.getId():1L);
                bookingDAO.createBooking(b);
                return "OK:"+code;
            }
            @Override protected void done(){
                btnCreate.setEnabled(true); ((JButton)btnCreate).setText("📋  Tạo đơn");
                try{
                    String res=get();
                    if("CONFLICT".equals(res)){JOptionPane.showMessageDialog(PanelTaoDon.this,"Sân đã được đặt trong khung giờ này.\nVui lòng chọn giờ khác.","Trùng lịch",JOptionPane.WARNING_MESSAGE);}
                    else{
                        JOptionPane.showMessageDialog(PanelTaoDon.this,"✅ Tạo đơn thành công!\nMã đơn: "+res.substring(3),"Thành công",JOptionPane.INFORMATION_MESSAGE);
                        doBack();
                    }
                }catch(Exception ex){JOptionPane.showMessageDialog(PanelTaoDon.this,"Lỗi: "+ex.getMessage(),"Lỗi",JOptionPane.ERROR_MESSAGE);}
            }
        }.execute();
    }

    private void doBack() {
        if(dashboard!=null) dashboard.showPanel(Dashboard.PANEL_DAT_LICH);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnCreate;
    private javax.swing.JComboBox cmbCourt;
    private javax.swing.JLabel lblCourtLabel;
    private javax.swing.JLabel lblCustName;
    private javax.swing.JLabel lblCustPhone;
    private javax.swing.JLabel lblDateLabel;
    private javax.swing.JLabel lblEndHint;
    private javax.swing.JLabel lblNotesLabel;
    private javax.swing.JLabel lblStartHint;
    private javax.swing.JLabel lblSubtitle;
    private javax.swing.JLabel lblTimeLabel;
    private javax.swing.JLabel lblTimeSep;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JPanel panelButtons;
    private javax.swing.JPanel panelForm;
    private javax.swing.JPanel panelOuter;
    private javax.swing.JPanel panelSummary;
    private javax.swing.JScrollPane scrNotes;
    private javax.swing.JScrollPane scrollOuter;
    private javax.swing.JSpinner spnDate;
    private javax.swing.JSpinner spnEnd;
    private javax.swing.JSpinner spnStart;
    private javax.swing.JTextArea txaNotes;
    private javax.swing.JTextField txtCustName;
    private javax.swing.JTextField txtCustPhone;
    // End of variables declaration//GEN-END:variables
}
