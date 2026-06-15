import sys
import sqlite3
import os
from PySide6.QtWidgets import QApplication, QWizard, QMessageBox
from wizard import Ui_Wizard


class Wizard(QWizard):
    def __init__(self):
        super().__init__()

        self.ui = Ui_Wizard()
        self.ui.setupUi(self)

        self.finished.connect(self.on_finished)

    def on_finished(self, result):
        if result == QWizard.DialogCode.Accepted:
            self.create_database()

    def create_database(self):
        name = self.ui.name.toPlainText().strip()
        year = self.ui.year.value()

        if not name:
            QMessageBox.warning(self, "Error", "Name cannot be empty.")
            return

        db_path = f"{name}.oswim"

        try:
            conn = sqlite3.connect(db_path)
            cursor = conn.cursor()

            # Main info table with wizard values
            cursor.execute("""
                CREATE TABLE IF NOT EXISTS info (
                    id    INTEGER PRIMARY KEY AUTOINCREMENT,
                    name  TEXT    NOT NULL,
                    year  INTEGER NOT NULL
                )
            """)
            cursor.execute(
                "INSERT INTO info (name, year) VALUES (?, ?)",
                (name, year)
            )

            # Empty races table
            cursor.execute("""
                CREATE TABLE IF NOT EXISTS races (
                    id          INTEGER PRIMARY KEY AUTOINCREMENT,
                    race_name   TEXT,
                    race_date   TEXT,
                    location    TEXT,
                    distance_km REAL
                )
            """)

            conn.commit()
            conn.close()

            QMessageBox.information(
                self,
                "Database created",
                f"File saved as:\n{os.path.abspath(db_path)}"
            )

        except Exception as e:
            QMessageBox.critical(self, "Database error", str(e))


def main():
    app = QApplication(sys.argv)
    app.setStyle("Fusion")

    wizard = Wizard()
    wizard.show()

    sys.exit(app.exec())


if __name__ == "__main__":
    main()