"""
oswim_app.py  –  Opens a .oswim database file.

Usage
-----
    python oswim_app.py path/to/file.oswim

Register as the default handler for .oswim files (see README below) so
double-clicking a file launches this app automatically.
"""

import sys
import sqlite3
import os

from PySide6.QtWidgets import (
    QApplication, QMainWindow, QWidget, QVBoxLayout,
    QHBoxLayout, QLabel, QTableWidget, QTableWidgetItem,
    QTabWidget, QStatusBar, QSizePolicy, QHeaderView,
    QMessageBox,
)
from PySide6.QtGui import QFont
from PySide6.QtCore import Qt


# ---------------------------------------------------------------------------
# Main window
# ---------------------------------------------------------------------------

class OSwimWindow(QMainWindow):
    def __init__(self, db_path: str):
        super().__init__()

        self.db_path = db_path
        self.setWindowTitle(f"oSwim – {os.path.basename(db_path)}")
        self.resize(800, 520)

        self._build_ui()
        self._load_database()

    # ------------------------------------------------------------------
    # UI construction
    # ------------------------------------------------------------------

    def _build_ui(self):
        central = QWidget()
        self.setCentralWidget(central)

        root = QVBoxLayout(central)
        root.setContentsMargins(12, 12, 12, 12)
        root.setSpacing(10)

        # ── Header ────────────────────────────────────────────────────
        self.header_label = QLabel("oSwim")
        font = QFont()
        font.setPointSize(16)
        font.setBold(True)
        self.header_label.setFont(font)
        self.header_label.setAlignment(Qt.AlignmentFlag.AlignLeft)
        root.addWidget(self.header_label)

        self.sub_label = QLabel("")
        root.addWidget(self.sub_label)

        # ── Tab widget ────────────────────────────────────────────────
        self.tabs = QTabWidget()
        root.addWidget(self.tabs)

        # Info tab
        info_widget = QWidget()
        info_layout = QVBoxLayout(info_widget)
        info_layout.setContentsMargins(8, 8, 8, 8)

        self.info_table = QTableWidget()
        self.info_table.setEditTriggers(QTableWidget.EditTrigger.NoEditTriggers)
        self.info_table.horizontalHeader().setSectionResizeMode(
            QHeaderView.ResizeMode.Stretch
        )
        info_layout.addWidget(self.info_table)
        self.tabs.addTab(info_widget, "Info")

        # Races tab
        races_widget = QWidget()
        races_layout = QVBoxLayout(races_widget)
        races_layout.setContentsMargins(8, 8, 8, 8)

        self.races_table = QTableWidget()
        self.races_table.setEditTriggers(QTableWidget.EditTrigger.NoEditTriggers)
        self.races_table.horizontalHeader().setSectionResizeMode(
            QHeaderView.ResizeMode.Stretch
        )
        races_layout.addWidget(self.races_table)
        self.tabs.addTab(races_widget, "Races")

        # Status bar
        self.status = QStatusBar()
        self.setStatusBar(self.status)

    # ------------------------------------------------------------------
    # Database loading
    # ------------------------------------------------------------------

    def _load_database(self):
        if not os.path.isfile(self.db_path):
            QMessageBox.critical(self, "File not found",
                                 f"Cannot open:\n{self.db_path}")
            return

        try:
            conn = sqlite3.connect(self.db_path)
            conn.row_factory = sqlite3.Row

            self._populate_table(conn, "info", self.info_table)
            self._populate_table(conn, "races", self.races_table)

            # Update header from the info row
            cursor = conn.cursor()
            cursor.execute("SELECT name, year FROM info LIMIT 1")
            row = cursor.fetchone()
            if row:
                self.header_label.setText(row["name"])
                self.sub_label.setText(f"Season {row['year']}")
                self.setWindowTitle(
                    f"oSwim – {row['name']} ({row['year']})"
                )

            conn.close()
            self.status.showMessage(f"Loaded: {os.path.abspath(self.db_path)}")

        except Exception as exc:
            QMessageBox.critical(self, "Database error", str(exc))

    @staticmethod
    def _populate_table(conn, table_name: str, widget: QTableWidget):
        """Read *table_name* from conn and fill *widget*."""
        try:
            cursor = conn.cursor()
            cursor.execute(f"SELECT * FROM {table_name}")   # noqa: S608
            rows = cursor.fetchall()

            if not rows:
                # Show column headers even when empty
                cursor.execute(f"PRAGMA table_info({table_name})")
                columns = [col[1] for col in cursor.fetchall()]
                widget.setColumnCount(len(columns))
                widget.setHorizontalHeaderLabels(columns)
                widget.setRowCount(0)
                return

            columns = rows[0].keys()
            widget.setColumnCount(len(columns))
            widget.setHorizontalHeaderLabels(columns)
            widget.setRowCount(len(rows))

            for r_idx, row in enumerate(rows):
                for c_idx, value in enumerate(row):
                    item = QTableWidgetItem(
                        "" if value is None else str(value)
                    )
                    item.setTextAlignment(Qt.AlignmentFlag.AlignCenter)
                    widget.setItem(r_idx, c_idx, item)

        except sqlite3.OperationalError:
            # Table might not exist yet
            widget.setRowCount(0)
            widget.setColumnCount(0)


# ---------------------------------------------------------------------------
# Entry point
# ---------------------------------------------------------------------------

def main():
    app = QApplication(sys.argv)
    app.setStyle("Fusion")

    # Accept a file path from the command line (double-click passes it here)
    if len(sys.argv) < 2:
        QMessageBox.warning(
            None,
            "No file",
            "Usage: oswim_app.py <file.oswim>\n\n"
            "Double-click a .oswim file to open it, or pass a path as argument.",
        )
        sys.exit(1)

    db_path = sys.argv[1]
    window = OSwimWindow(db_path)
    window.show()

    sys.exit(app.exec())


if __name__ == "__main__":
    main()