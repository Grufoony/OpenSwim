use iced::{Task, Element, Theme, Length};
use iced::widget::{button, column, container, row, text};

pub fn run() -> iced::Result {
    iced::application(OpenSwimApp::new, OpenSwimApp::update, OpenSwimApp::view)
        .title("OpenSwim")
        .theme(OpenSwimApp::theme)
        .run()
}

struct OpenSwimApp {
    show_info: bool,
    show_file_menu: bool,
}

impl Default for OpenSwimApp {
    fn default() -> Self {
        Self {
            show_info: false,
            show_file_menu: false,
        }
    }
}

#[derive(Debug, Clone)]
enum Message {
    FilePressed,
    ImportDB,
    ImportCSV,
    CloseFileMenu,
    InfoPressed,
    CloseInfo,
}

impl OpenSwimApp {
    /// Create a new instance of the main application.
    /// Returns the instance and an initial task.
    fn new() -> (Self, Task<Message>) {
        (Self { show_info: false, show_file_menu: false }, Task::none())
    }

    fn update(&mut self, message: Message) -> Task<Message> {
        match message {
            /// Handle File button actions
            Message::FilePressed => {
                self.show_file_menu = !self.show_file_menu;
                Task::none()
            }
            Message::ImportDB => {
                println!("Import DB selected");
                self.show_file_menu = false;
                Task::none()
            }
            Message::ImportCSV => {
                println!("Import CSV selected");
                self.show_file_menu = false;
                Task::none()
            }
            Message::CloseFileMenu => {
                self.show_file_menu = false;
                Task::none()
            }
            /// Handle Info button actions
            Message::InfoPressed => {
                self.show_info = true;
                Task::none()
            }
            Message::CloseInfo => {
                self.show_info = false;
                Task::none()
            }
        }
    }

    fn view(&self) -> Element<'_, Message> {
        let menu = row![
            button("File").on_press(Message::FilePressed),
            button("Info").on_press(Message::InfoPressed)
        ]
        .spacing(10)
        .padding(10);

        let content = container(text("Default App Window"))
            .center_x(Length::Fill)
            .center_y(Length::Fill)
            .width(Length::Fill)
            .height(Length::Fill);

        // Build base view conditionally based on file menu state
        let base_view = if self.show_file_menu {
            let file_menu = container(
                column![
                    button("Import DB").on_press(Message::ImportDB).width(Length::Fill),
                    button("Import CSV").on_press(Message::ImportCSV).width(Length::Fill),
                ]
                .spacing(5)
                .padding(5)
            )
            .width(Length::Fixed(150.0))
            .padding(5)
            .style(|theme: &Theme| {
                container::Style {
                    background: Some(theme.palette().background.into()),
                    border: iced::Border {
                        color: theme.palette().primary,
                        width: 1.0,
                        radius: 5.0.into(),
                    },
                    ..Default::default()
                }
            });

            // Position the file menu below the File button
            let file_menu_positioned = container(file_menu)
                .padding([0, 10]); // [vertical, horizontal] padding

            column![menu, file_menu_positioned, content]
        } else {
            column![menu, content]
        };

        if self.show_info {
            let info_content = column![
                text("OpenSwim").size(24),
                text(""),
                text(format!("Version: {}", env!("CARGO_PKG_VERSION"))),
                text(format!("Author: {}", env!("CARGO_PKG_AUTHORS"))),
                text(""),
                text("GitHub: https://github.com/grufoony/OpenSwim"),
                text(""),
                button("Close").on_press(Message::CloseInfo),
            ]
            .spacing(10)
            .padding(20);

            let info_modal = container(info_content)
                .width(Length::Fixed(400.0))
                .padding(20)
                .style(|theme: &Theme| {
                    container::Style {
                        background: Some(theme.palette().background.into()),
                        border: iced::Border {
                            color: theme.palette().primary,
                            width: 2.0,
                            radius: 10.0.into(),
                        },
                        ..Default::default()
                    }
                });

            let overlay = container(
                container(info_modal)
                    .center_x(Length::Fill)
                    .center_y(Length::Fill)
                    .width(Length::Fill)
                    .height(Length::Fill)
                    .style(|_theme: &Theme| {
                        container::Style {
                            background: Some(iced::Color::from_rgba(0.0, 0.0, 0.0, 0.7).into()),
                            ..Default::default()
                        }
                    })
            )
            .width(Length::Fill)
            .height(Length::Fill);

            iced::widget::stack![base_view, overlay].into()
        } else {
            base_view.into()
        }
    }

    fn theme(&self) -> Theme {
        Theme::Dark
    }
}
