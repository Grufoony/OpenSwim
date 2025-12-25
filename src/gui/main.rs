use iced::{Task, Element, Theme, Length};
use iced::widget::{button, column, container, row, text};

pub fn run() -> iced::Result {
    iced::application(OpenSwimApp::new, OpenSwimApp::update, OpenSwimApp::view)
        .title("OpenSwim")
        .theme(OpenSwimApp::theme)
        .run()
}

#[derive(Default)]
struct OpenSwimApp;

#[derive(Debug, Clone)]
enum Message {
    FilePressed,
    InfoPressed,
}

impl OpenSwimApp {
    /// Create a new instance of the main application.
    /// Returns the instance and an initial task.
    fn new() -> (Self, Task<Message>) {
        (Self, Task::none())
    }

    fn update(&mut self, message: Message) -> Task<Message> {
        match message {
            Message::FilePressed => {
                println!("File menu pressed");
                Task::none()
            }
            Message::InfoPressed => {
                println!("Info menu pressed");
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

        column![menu, content].into()
    }

    fn theme(&self) -> Theme {
        Theme::Dark
    }
}
