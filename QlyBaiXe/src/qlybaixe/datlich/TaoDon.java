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
import qlybaixe.model.Booking;
import qlybaixe.model.Court;
import qlybaixe.model.User;

/**
 * Form tạo đơn đặt sân mới – chuẩn NetBeans JFrame Form
 */
public class TaoDon extends javax.swing.JFrame {

    // ── Màu ─────────────────────────────────────────────────────
    private static final Color PRIMARY    = new Color(13, 71, 161);
    private static final Color PRIMARY_LT = new Color(239, 246, 255);
    private static final Color SIDEBAR_BG = new Color(249, 250, 251);
    private static final Color SIDEBAR_SEL= new Color(235, 240, 255);
    private static final Color TEXT_DARK  = new Color(30,  41,  59);
    private static final Color TEXT_GRAY  = new Color(107, 114, 128);
    private static final Color BORDER_CLR = new Color(229, 231, 235);

    private static final DecimalFormat FMT = new DecimalFormat("#,###");

    // ── DAO / State ──────────────────────────────────────────────
    private final BookingDAO bookingDAO = new BookingDAO();
    private final CourtDAO   courtDAO   = new CourtDAO();
    private final User     currentUser;
    private final DatLich  parentFrame;
    private List<Court>    courts;
    private JButton selectedNav;

    // ── Summary labels ─────────────────────────────────────────
    private JLabel valSan, valThoiGian, valGia, valTong;

    public TaoDon(User user, DatLich parent) {
        this.currentUser = user;
        this.parentFrame = parent;
        initComponents();
        setupAfterInit();
    }

    public TaoDon() { this(null, null); }

    // ════════════════════════════════════════════════════════════
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelHeader  = new javax.swing.JPanel();
        lblAppTitle  = new javax.swing.JLabel();
        lblUserName  = new javax.swing.JLabel();
        lblUserRole  = new javax.swing.JLabel();
        btnLogout    = new javax.swing.JButton();
        panelMain    = new javax.swing.JPanel();
        panelSidebar = new javax.swing.JPanel();
        btnTongQuan       = new javax.swing.JButton();
        btnDatLich        = new javax.swing.JButton();
        btnLichSan        = new javax.swing.JButton();
        btnQuanLySan      = new javax.swing.JButton();
        btnQuanLyNV       = new javax.swing.JButton();
        btnBaoCao         = new javax.swing.JButton();
        btnLichSu         = new javax.swing.JButton();
        panelContent = new javax.swing.JPanel();
        btnBack      = new javax.swing.JButton();
        lblTitle     = new javax.swing.JLabel();
        lblSubtitle  = new javax.swing.JLabel();
        panelForm    = new javax.swing.JPanel();
        lblCourtLabel     = new javax.swing.JLabel();
        cmbCourt          = new javax.swing.JComboBox<>();
        lblCustName       = new javax.swing.JLabel();
        txtCustName       = new javax.swing.JTextField();
        lblCustPhone      = new javax.swing.JLabel();
        txtCustPhone      = new javax.swing.JTextField();
        lblDateLabel      = new javax.swing.JLabel();
        spnDate           = new javax.swing.JSpinner();
        lblTimeLabel      = new javax.swing.JLabel();
        spnStart          = new javax.swing.JSpinner();
        lblTimeSep        = new javax.swing.JLabel();
        spnEnd            = new javax.swing.JSpinner();
        lblStartHint      = new javax.swing.JLabel();
        lblEndHint        = new javax.swing.JLabel();
        lblNotesLabel     = new javax.swing.JLabel();
        scrNotes          = new javax.swing.JScrollPane();
        txaNotes          = new javax.swing.JTextArea();
        panelSummary = new javax.swing.JPanel();
        btnCreate    = new javax.swing.JButton();
        btnCancel    = new javax.swing.JButton();

        // ── Frame ────────────────────────────────────────────────
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Tạo đơn đặt sân mới");
        setMinimumSize(new java.awt.Dimension(1100, 700));

        // ── Header ───────────────────────────────────────────────
        panelHeader.setBackground(java.awt.Color.WHITE);
        panelHeader.setBorder(javax.swing.BorderFactory.createMatteBorder(0,0,1,0, BORDER_CLR));

        lblAppTitle.setFont(new java.awt.Font("Segoe UI", 1, 16));
        lblAppTitle.setForeground(PRIMARY);
        lblAppTitle.setText("Quản Lý Sân Thể Thao");

        lblUserName.setFont(new java.awt.Font("Segoe UI", 1, 13));
        lblUserName.setForeground(TEXT_DARK);
        lblUserName.setText("Người dùng");

        lblUserRole.setFont(new java.awt.Font("Segoe UI", 0, 11));
        lblUserRole.setForeground(TEXT_GRAY);
        lblUserRole.setText("Nhân viên");

        btnLogout.setBackground(new java.awt.Color(254,242,242));
        btnLogout.setFont(new java.awt.Font("Segoe UI", 1, 12));
        btnLogout.setForeground(new java.awt.Color(239,68,68));
        btnLogout.setText("→ Đăng xuất");
        btnLogout.setBorderPainted(false);
        btnLogout.setFocusPainted(false);
        btnLogout.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLogout.addActionListener(e -> doLogout());

        javax.swing.GroupLayout headerLayout = new javax.swing.GroupLayout(panelHeader);
        panelHeader.setLayout(headerLayout);
        headerLayout.setHorizontalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerLayout.createSequentialGroup()
                .addGap(20).addComponent(lblAppTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                    javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblUserName).addGap(2).addComponent(lblUserRole)
                .addGap(16).addComponent(btnLogout).addGap(20))
        );
        headerLayout.setVerticalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAppTitle).addComponent(lblUserName).addComponent(btnLogout))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, headerLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblUserRole).addGap(6))
        );

        // ── Sidebar ──────────────────────────────────────────────
        panelSidebar.setBackground(SIDEBAR_BG);
        panelSidebar.setBorder(javax.swing.BorderFactory.createMatteBorder(0,0,0,1, BORDER_CLR));
        panelSidebar.setPreferredSize(new java.awt.Dimension(170, 600));

        btnTongQuan  = makeSidebarBtn("  \uD83D\uDCCA  Tổng quan");
        btnDatLich   = makeSidebarBtn("  \uD83D\uDCC5  Đặt lịch");
        btnLichSan   = makeSidebarBtn("  \uD83D\uDDD3  Lịch sân");
        btnQuanLySan = makeSidebarBtn("  \u229E  Quản lý sân");
        btnQuanLyNV  = makeSidebarBtn("  \uD83D\uDC64  Quản lý nhân viên");
        btnBaoCao    = makeSidebarBtn("  \uD83D\uDCCA  Báo cáo doanh thu");
        btnLichSu    = makeSidebarBtn("  \uD83D\uDD53  Lịch sử");

        javax.swing.GroupLayout sideLayout = new javax.swing.GroupLayout(panelSidebar);
        panelSidebar.setLayout(sideLayout);
        sideLayout.setHorizontalGroup(
            sideLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnTongQuan, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
            .addComponent(btnDatLich,  javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
            .addComponent(btnLichSan,  javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
            .addComponent(btnQuanLySan,javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
            .addComponent(btnQuanLyNV, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
            .addComponent(btnBaoCao,   javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
            .addComponent(btnLichSu,   javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
        );
        sideLayout.setVerticalGroup(
            sideLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sideLayout.createSequentialGroup()
                .addGap(12)
                .addComponent(btnTongQuan, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(2)
                .addComponent(btnDatLich,  javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(2)
                .addComponent(btnLichSan,  javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(2)
                .addComponent(btnQuanLySan,javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(2)
                .addComponent(btnQuanLyNV, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(2)
                .addComponent(btnBaoCao,   javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(2)
                .addComponent(btnLichSu,   javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        // ── Content ──────────────────────────────────────────────
        panelContent.setBackground(new java.awt.Color(245, 246, 250));

        btnBack.setText("←");
        btnBack.setFont(new java.awt.Font("Segoe UI", 1, 18));
        btnBack.setForeground(TEXT_GRAY);
        btnBack.setBackground(new java.awt.Color(245, 246, 250));
        btnBack.setBorderPainted(false);
        btnBack.setFocusPainted(false);
        btnBack.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBack.addActionListener(e -> doBack());

        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 20));
        lblTitle.setForeground(TEXT_DARK);
        lblTitle.setText("Tạo đơn đặt sân mới");

        lblSubtitle.setFont(new java.awt.Font("Segoe UI", 0, 13));
        lblSubtitle.setForeground(TEXT_GRAY);
        lblSubtitle.setText("Nhập thông tin khách hàng và lịch đặt sân");

        // ── Form card ─────────────────────────────────────────────
        panelForm.setBackground(java.awt.Color.WHITE);
        panelForm.setBorder(javax.swing.BorderFactory.createLineBorder(BORDER_CLR, 1, true));

        lblCourtLabel.setFont(new java.awt.Font("Segoe UI", 1, 12));
        lblCourtLabel.setText("Chọn sân *");
        lblCourtLabel.setForeground(TEXT_DARK);

        cmbCourt.setFont(new java.awt.Font("Segoe UI", 0, 13));
        cmbCourt.setPreferredSize(new java.awt.Dimension(400, 36));

        lblCustName.setFont(new java.awt.Font("Segoe UI", 1, 12));
        lblCustName.setText("Tên khách hàng *");
        lblCustName.setForeground(TEXT_DARK);

        txtCustName.setFont(new java.awt.Font("Segoe UI", 0, 13));
        txtCustName.putClientProperty("JTextField.placeholderText", "Nguyễn Văn A");

        lblCustPhone.setFont(new java.awt.Font("Segoe UI", 1, 12));
        lblCustPhone.setText("Số điện thoại *");
        lblCustPhone.setForeground(TEXT_DARK);

        txtCustPhone.setFont(new java.awt.Font("Segoe UI", 0, 13));
        txtCustPhone.putClientProperty("JTextField.placeholderText", "0912345678");

        lblDateLabel.setFont(new java.awt.Font("Segoe UI", 1, 12));
        lblDateLabel.setText("Ngày chơi *");
        lblDateLabel.setForeground(TEXT_DARK);

        // Date spinner
        spnDate.setModel(new javax.swing.SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spnDate, "dd/MM/yyyy");
        spnDate.setEditor(dateEditor);
        spnDate.setFont(new java.awt.Font("Segoe UI", 0, 13));
        spnDate.setPreferredSize(new java.awt.Dimension(160, 36));
        ((JSpinner.DefaultEditor) spnDate.getEditor()).getTextField().setFont(new java.awt.Font("Segoe UI", 0, 13));

        lblTimeLabel.setFont(new java.awt.Font("Segoe UI", 1, 12));
        lblTimeLabel.setText("Thời gian *");
        lblTimeLabel.setForeground(TEXT_DARK);

        // Start time spinner
        Calendar calStart = Calendar.getInstance();
        calStart.set(Calendar.HOUR_OF_DAY, 8);
        calStart.set(Calendar.MINUTE, 0);
        calStart.set(Calendar.SECOND, 0);
        spnStart.setModel(new javax.swing.SpinnerDateModel(calStart.getTime(), null, null, Calendar.MINUTE));
        JSpinner.DateEditor startEditor = new JSpinner.DateEditor(spnStart, "HH:mm");
        spnStart.setEditor(startEditor);
        spnStart.setPreferredSize(new java.awt.Dimension(100, 36));
        ((JSpinner.DefaultEditor) spnStart.getEditor()).getTextField().setFont(new java.awt.Font("Segoe UI", 0, 13));

        // End time spinner
        Calendar calEnd = Calendar.getInstance();
        calEnd.set(Calendar.HOUR_OF_DAY, 10);
        calEnd.set(Calendar.MINUTE, 0);
        calEnd.set(Calendar.SECOND, 0);
        spnEnd.setModel(new javax.swing.SpinnerDateModel(calEnd.getTime(), null, null, Calendar.MINUTE));
        JSpinner.DateEditor endEditor = new JSpinner.DateEditor(spnEnd, "HH:mm");
        spnEnd.setEditor(endEditor);
        spnEnd.setPreferredSize(new java.awt.Dimension(100, 36));
        ((JSpinner.DefaultEditor) spnEnd.getEditor()).getTextField().setFont(new java.awt.Font("Segoe UI", 0, 13));

        lblTimeSep.setText("–");
        lblTimeSep.setFont(new java.awt.Font("Segoe UI", 1, 14));

        lblStartHint.setText("Bắt đầu");
        lblStartHint.setFont(new java.awt.Font("Segoe UI", 0, 11));
        lblStartHint.setForeground(TEXT_GRAY);

        lblEndHint.setText("Kết thúc");
        lblEndHint.setFont(new java.awt.Font("Segoe UI", 0, 11));
        lblEndHint.setForeground(TEXT_GRAY);

        lblNotesLabel.setFont(new java.awt.Font("Segoe UI", 1, 12));
        lblNotesLabel.setText("Ghi chú");
        lblNotesLabel.setForeground(TEXT_DARK);

        txaNotes.setFont(new java.awt.Font("Segoe UI", 0, 13));
        txaNotes.setRows(4);
        txaNotes.setLineWrap(true);
        txaNotes.setWrapStyleWord(true);
        scrNotes.setViewportView(txaNotes);
        scrNotes.setPreferredSize(new java.awt.Dimension(400, 90));

        // Layout form card
        javax.swing.GroupLayout formLayout = new javax.swing.GroupLayout(panelForm);
        panelForm.setLayout(formLayout);

        // Time row panel
        JPanel timeRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        timeRow.setOpaque(false);
        JPanel startBox = new JPanel(new BorderLayout(0,2));
        startBox.setOpaque(false);
        startBox.add(spnStart, BorderLayout.CENTER);
        startBox.add(lblStartHint, BorderLayout.SOUTH);
        JPanel endBox = new JPanel(new BorderLayout(0,2));
        endBox.setOpaque(false);
        endBox.add(spnEnd, BorderLayout.CENTER);
        endBox.add(lblEndHint, BorderLayout.SOUTH);
        timeRow.add(startBox);
        timeRow.add(lblTimeSep);
        timeRow.add(endBox);

        formLayout.setHorizontalGroup(
            formLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(formLayout.createSequentialGroup()
                .addGap(24)
                .addGroup(formLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCourtLabel)
                    .addComponent(cmbCourt, javax.swing.GroupLayout.DEFAULT_SIZE, 560, Short.MAX_VALUE)
                    .addGroup(formLayout.createSequentialGroup()
                        .addGroup(formLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCustName)
                            .addComponent(txtCustName, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20)
                        .addGroup(formLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCustPhone)
                            .addComponent(txtCustPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(formLayout.createSequentialGroup()
                        .addGroup(formLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDateLabel)
                            .addComponent(spnDate, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20)
                        .addGroup(formLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTimeLabel)
                            .addComponent(timeRow)))
                    .addComponent(lblNotesLabel)
                    .addComponent(scrNotes, javax.swing.GroupLayout.DEFAULT_SIZE, 560, Short.MAX_VALUE)
                    .addComponent(panelSummary, javax.swing.GroupLayout.DEFAULT_SIZE, 560, Short.MAX_VALUE))
                .addGap(24))
        );
        formLayout.setVerticalGroup(
            formLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(formLayout.createSequentialGroup()
                .addGap(20)
                .addComponent(lblCourtLabel)
                .addGap(4).addComponent(cmbCourt, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16)
                .addGroup(formLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCustName).addComponent(lblCustPhone))
                .addGap(4)
                .addGroup(formLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCustName, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCustPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16)
                .addGroup(formLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblDateLabel).addComponent(lblTimeLabel))
                .addGap(4)
                .addGroup(formLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(spnDate, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(timeRow))
                .addGap(16)
                .addComponent(lblNotesLabel)
                .addGap(4)
                .addComponent(scrNotes, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16)
                .addComponent(panelSummary, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20))
        );

        // ── Nút tạo đơn + hủy ────────────────────────────────────
        btnCreate.setBackground(PRIMARY);
        btnCreate.setFont(new java.awt.Font("Segoe UI", 1, 14));
        btnCreate.setForeground(java.awt.Color.WHITE);
        btnCreate.setText("📋  Tạo đơn");
        btnCreate.setBorderPainted(false);
        btnCreate.setFocusPainted(false);
        btnCreate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCreate.addActionListener(e -> doCreateBooking());

        btnCancel.setBackground(new java.awt.Color(241, 245, 249));
        btnCancel.setFont(new java.awt.Font("Segoe UI", 0, 13));
        btnCancel.setForeground(TEXT_DARK);
        btnCancel.setText("Hủy");
        btnCancel.setBorderPainted(false);
        btnCancel.setFocusPainted(false);
        btnCancel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancel.addActionListener(e -> doBack());

        // Layout content
        javax.swing.GroupLayout contentLayout = new javax.swing.GroupLayout(panelContent);
        panelContent.setLayout(contentLayout);

        // Scrollable form wrapper
        JScrollPane formScroll = new JScrollPane(panelForm);
        formScroll.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        formScroll.getViewport().setBackground(new java.awt.Color(245, 246, 250));
        formScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        contentLayout.setHorizontalGroup(
            contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contentLayout.createSequentialGroup()
                .addGap(24)
                .addGroup(contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(contentLayout.createSequentialGroup()
                        .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4).addGroup(contentLayout.createParallelGroup()
                            .addComponent(lblTitle).addComponent(lblSubtitle)))
                    .addComponent(formScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE)
                    .addGroup(contentLayout.createSequentialGroup()
                        .addComponent(btnCreate, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10)
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(24))
        );
        contentLayout.setVerticalGroup(
            contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contentLayout.createSequentialGroup()
                .addGap(20)
                .addGroup(contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btnBack).addComponent(lblTitle))
                .addGap(2).addComponent(lblSubtitle)
                .addGap(16)
                .addComponent(formScroll, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(14)
                .addGroup(contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCreate, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20))
        );

        // ── panelMain ────────────────────────────────────────────
        panelMain.setBackground(new java.awt.Color(245, 246, 250));
        javax.swing.GroupLayout mainLayout = new javax.swing.GroupLayout(panelMain);
        panelMain.setLayout(mainLayout);
        mainLayout.setHorizontalGroup(
            mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainLayout.createSequentialGroup()
                .addComponent(panelSidebar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(panelContent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        mainLayout.setVerticalGroup(
            mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelSidebar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelContent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        // ── Root ─────────────────────────────────────────────────
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelMain,   javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelHeader, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(panelMain,   javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // ════════════════════════════════════════════════════════════
    //  Setup sau initComponents
    // ════════════════════════════════════════════════════════════
    private void setupAfterInit() {
        setLocationRelativeTo(null);
        if (currentUser != null) {
            lblUserName.setText(currentUser.getFullName());
            lblUserRole.setText("ADMIN".equals(currentUser.getRole()) ? "Quản trị viên" : "Nhân viên");
        }

        selectNav(btnDatLich);
        setupNavActions();
        buildSummaryPanel();
        loadCourts();

        // Lắng nghe thay đổi để cập nhật summary
        cmbCourt.addActionListener(e -> updateSummary());
        spnStart.addChangeListener(e -> updateSummary());
        spnEnd.addChangeListener(e -> updateSummary());
    }

    private JButton makeSidebarBtn(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setForeground(TEXT_DARK);
        btn.setBackground(SIDEBAR_BG);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 16, 0, 16));
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseEntered(java.awt.event.MouseEvent e) {
                if (btn != selectedNav) btn.setBackground(new Color(241, 245, 249));
            }
            @Override public void mouseExited(java.awt.event.MouseEvent e) {
                if (btn != selectedNav) btn.setBackground(SIDEBAR_BG);
            }
        });
        return btn;
    }

    private void selectNav(JButton btn) {
        if (selectedNav != null) {
            selectedNav.setBackground(SIDEBAR_BG);
            selectedNav.setForeground(TEXT_DARK);
            selectedNav.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        }
        selectedNav = btn;
        btn.setBackground(SIDEBAR_SEL);
        btn.setForeground(PRIMARY);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
    }

    private void setupNavActions() {
        btnTongQuan.addActionListener(e -> {
            new qlybaixe.dashboard.Dashboard(currentUser).setVisible(true);
            dispose();
        });
        btnDatLich.addActionListener(e -> doBack());
    }

    // ── Build Summary Panel ─────────────────────────────────────
    private void buildSummaryPanel() {
        panelSummary.setLayout(new GridBagLayout());
        panelSummary.setBackground(PRIMARY_LT);
        panelSummary.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createLineBorder(new Color(191, 219, 254), 1, true),
            javax.swing.BorderFactory.createEmptyBorder(14, 18, 14, 18)));

        GridBagConstraints gL = new GridBagConstraints();
        gL.gridx = 0; gL.anchor = GridBagConstraints.WEST;
        gL.insets = new Insets(2, 0, 2, 0); gL.weightx = 0.5;

        GridBagConstraints gR = new GridBagConstraints();
        gR.gridx = 1; gR.anchor = GridBagConstraints.EAST;
        gR.insets = new Insets(2, 0, 2, 0); gR.weightx = 0.5; gR.fill = GridBagConstraints.HORIZONTAL;

        JLabel hdr = new JLabel("Tóm tắt đơn hàng");
        hdr.setFont(new Font("Segoe UI", Font.BOLD, 13));
        hdr.setForeground(TEXT_DARK);
        GridBagConstraints hdrG = new GridBagConstraints();
        hdrG.gridx = 0; hdrG.gridy = 0; hdrG.gridwidth = 2;
        hdrG.anchor = GridBagConstraints.WEST; hdrG.insets = new Insets(0,0,8,0);
        panelSummary.add(hdr, hdrG);

        valSan       = addSummaryRow(panelSummary, "Sân:",       "—",     1, gL, gR, false);
        valThoiGian  = addSummaryRow(panelSummary, "Thời gian:", "—",     2, gL, gR, false);
        valGia       = addSummaryRow(panelSummary, "Giá/giờ:",   "—",     3, gL, gR, false);

        // Divider
        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(191, 219, 254));
        GridBagConstraints sepG = new GridBagConstraints();
        sepG.gridx = 0; sepG.gridy = 4; sepG.gridwidth = 2;
        sepG.fill = GridBagConstraints.HORIZONTAL; sepG.insets = new Insets(6,0,6,0);
        panelSummary.add(sep, sepG);

        // Total row
        JLabel lbTong = new JLabel("Tổng tiền:");
        lbTong.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lbTong.setForeground(TEXT_DARK);
        GridBagConstraints gL5 = new GridBagConstraints();
        gL5.gridx=0; gL5.gridy=5; gL5.anchor=GridBagConstraints.WEST; gL5.insets=new Insets(2,0,2,0);
        panelSummary.add(lbTong, gL5);

        valTong = new JLabel("0 đ");
        valTong.setFont(new Font("Segoe UI", Font.BOLD, 16));
        valTong.setForeground(PRIMARY);
        valTong.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gR5 = new GridBagConstraints();
        gR5.gridx=1; gR5.gridy=5; gR5.anchor=GridBagConstraints.EAST;
        gR5.fill=GridBagConstraints.HORIZONTAL; gR5.insets=new Insets(2,0,2,0);
        panelSummary.add(valTong, gR5);
    }

    private JLabel addSummaryRow(JPanel p, String labelText, String initVal, int y,
                                  GridBagConstraints gL, GridBagConstraints gR, boolean bold) {
        JLabel lbl = new JLabel(labelText);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lbl.setForeground(TEXT_GRAY);
        GridBagConstraints cL = (GridBagConstraints) gL.clone(); cL.gridy = y;
        p.add(lbl, cL);

        JLabel val = new JLabel(initVal);
        val.setFont(new Font("Segoe UI", bold ? Font.BOLD : Font.PLAIN, 12));
        val.setForeground(bold ? PRIMARY : TEXT_DARK);
        val.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints cR = (GridBagConstraints) gR.clone(); cR.gridy = y;
        p.add(val, cR);
        return val;
    }

    // ── Load courts ─────────────────────────────────────────────
    private void loadCourts() {
        new SwingWorker<List<Court>, Void>() {
            @Override protected List<Court> doInBackground() throws Exception {
                return courtDAO.getAllActiveCourts();
            }
            @Override protected void done() {
                try {
                    courts = get();
                    cmbCourt.removeAllItems();
                    for (Court c : courts) {
                        cmbCourt.addItem(c.getName() + " – " + FMT.format(c.getPricePerHour()) + "đ/giờ");
                    }
                    updateSummary();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(TaoDon.this,
                            "Lỗi tải danh sách sân: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        }.execute();
    }

    // ── Cập nhật tóm tắt khi thay đổi dữ liệu ───────────────────
    private void updateSummary() {
        int idx = cmbCourt.getSelectedIndex();
        if (courts == null || idx < 0 || idx >= courts.size()) return;

        Court c = courts.get(idx);
        valSan.setText(c.getName());
        valGia.setText(FMT.format(c.getPricePerHour()) + "đ");

        // Tính giờ
        try {
            Date dStart = (Date) spnStart.getValue();
            Date dEnd   = (Date) spnEnd.getValue();
            long diffMs = dEnd.getTime() - dStart.getTime();
            if (diffMs <= 0) {
                valThoiGian.setText("Thời gian không hợp lệ");
                valTong.setText("—");
                return;
            }
            double hours = diffMs / 3_600_000.0;
            String hStr = hours == Math.floor(hours)
                    ? (int) hours + " giờ"
                    : String.format("%.1f giờ", hours);
            valThoiGian.setText(hStr);

            BigDecimal total = c.getPricePerHour()
                    .multiply(BigDecimal.valueOf(hours))
                    .setScale(0, RoundingMode.HALF_UP);
            valTong.setText(FMT.format(total) + "đ");
        } catch (Exception ex) {
            valThoiGian.setText("—");
            valTong.setText("—");
        }
    }

    // ── Xử lý tạo đơn ──────────────────────────────────────────
    private void doCreateBooking() {
        // Validate
        String custName  = txtCustName.getText().trim();
        String custPhone = txtCustPhone.getText().trim();

        if (custName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên khách hàng.", "Lỗi", JOptionPane.WARNING_MESSAGE);
            txtCustName.requestFocus(); return;
        }
        if (custPhone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số điện thoại.", "Lỗi", JOptionPane.WARNING_MESSAGE);
            txtCustPhone.requestFocus(); return;
        }

        int idx = cmbCourt.getSelectedIndex();
        if (courts == null || idx < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sân.", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Date dDate  = (Date) spnDate.getValue();
        Date dStart = (Date) spnStart.getValue();
        Date dEnd   = (Date) spnEnd.getValue();
        long diffMs = dEnd.getTime() - dStart.getTime();
        if (diffMs <= 0) {
            JOptionPane.showMessageDialog(this, "Giờ kết thúc phải sau giờ bắt đầu.", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Court   court      = courts.get(idx);
        LocalDate  bookDate = dDate.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        LocalTime  startT   = dStart.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalTime().withSecond(0).withNano(0);
        LocalTime  endT     = dEnd.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalTime().withSecond(0).withNano(0);

        double hours = diffMs / 3_600_000.0;
        BigDecimal durHours = BigDecimal.valueOf(hours).setScale(1, RoundingMode.HALF_UP);
        BigDecimal total    = court.getPricePerHour().multiply(durHours).setScale(0, RoundingMode.HALF_UP);

        btnCreate.setEnabled(false);
        btnCreate.setText("Đang xử lý...");

        new SwingWorker<String, Void>() {
            @Override protected String doInBackground() throws Exception {
                // Kiểm tra trùng lịch
                if (bookingDAO.hasConflict(court.getId(), bookDate, startT, endT, null)) {
                    return "CONFLICT";
                }
                // Tạo mã đơn
                String code = bookingDAO.generateBookingCode(bookDate);
                // Tạo booking object
                Booking b = new Booking();
                b.setBookingCode(code);
                b.setCourtId(court.getId());
                b.setCustomerName(custName);
                b.setCustomerPhone(custPhone);
                b.setBookingDate(bookDate);
                b.setStartTime(startT);
                b.setEndTime(endT);
                b.setDurationHours(durHours);
                b.setPricePerHour(court.getPricePerHour());
                b.setTotalAmount(total);
                b.setNotes(txaNotes.getText().trim());
                b.setCreatedBy(currentUser != null && currentUser.getId() != null
                        ? currentUser.getId() : 1L);
                bookingDAO.createBooking(b);
                return "OK:" + code;
            }
            @Override protected void done() {
                btnCreate.setEnabled(true);
                btnCreate.setText("📋  Tạo đơn");
                try {
                    String result = get();
                    if ("CONFLICT".equals(result)) {
                        JOptionPane.showMessageDialog(TaoDon.this,
                            "Sân này đã được đặt trong khung giờ trên.\nVui lòng chọn giờ khác.",
                            "Trùng lịch", JOptionPane.WARNING_MESSAGE);
                    } else {
                        String code = result.substring(3);
                        JOptionPane.showMessageDialog(TaoDon.this,
                            "✅ Tạo đơn thành công!\nMã đơn: " + code,
                            "Thành công", JOptionPane.INFORMATION_MESSAGE);
                        doBack();
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(TaoDon.this,
                        "Lỗi tạo đơn: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        }.execute();
    }

    private void doBack() {
        if (parentFrame != null) {
            parentFrame.loadData();
            parentFrame.setVisible(true);
        } else {
            new DatLich(currentUser).setVisible(true);
        }
        dispose();
    }

    private void doLogout() {
        int ok = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc muốn đăng xuất?", "Xác nhận",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (ok == JOptionPane.YES_OPTION) {
            new qlybaixe.login.Login().setVisible(true);
            dispose();
        }
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
        catch (Exception ignored) {}
        java.awt.EventQueue.invokeLater(() -> new TaoDon().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnBaoCao;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnCreate;
    private javax.swing.JButton btnDatLich;
    private javax.swing.JButton btnLichSan;
    private javax.swing.JButton btnLichSu;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnQuanLyNV;
    private javax.swing.JButton btnQuanLySan;
    private javax.swing.JButton btnTongQuan;
    private javax.swing.JComboBox<String> cmbCourt;
    private javax.swing.JLabel  lblAppTitle;
    private javax.swing.JLabel  lblCourtLabel;
    private javax.swing.JLabel  lblCustName;
    private javax.swing.JLabel  lblCustPhone;
    private javax.swing.JLabel  lblDateLabel;
    private javax.swing.JLabel  lblEndHint;
    private javax.swing.JLabel  lblNotesLabel;
    private javax.swing.JLabel  lblStartHint;
    private javax.swing.JLabel  lblSubtitle;
    private javax.swing.JLabel  lblTimeSep;
    private javax.swing.JLabel  lblTimeLabel;
    private javax.swing.JLabel  lblTitle;
    private javax.swing.JLabel  lblUserName;
    private javax.swing.JLabel  lblUserRole;
    private javax.swing.JPanel  panelContent;
    private javax.swing.JPanel  panelForm;
    private javax.swing.JPanel  panelHeader;
    private javax.swing.JPanel  panelMain;
    private javax.swing.JPanel  panelSidebar;
    private javax.swing.JPanel  panelSummary;
    private javax.swing.JScrollPane scrNotes;
    private javax.swing.JSpinner spnDate;
    private javax.swing.JSpinner spnEnd;
    private javax.swing.JSpinner spnStart;

    private javax.swing.JTextArea txaNotes;
    private javax.swing.JTextField txtCustName;
    private javax.swing.JTextField txtCustPhone;
    // End of variables declaration//GEN-END:variables
}
