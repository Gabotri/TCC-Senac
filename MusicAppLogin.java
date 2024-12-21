//imports do javafx
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;

//classe da criação de login
public class MusicAppLogin extends Application {
    // substitui o inicio da aplicação para o login
    @Override
    public void start(Stage primaryStage) {
        // Título do app
        Label appTitle = new Label("Music Center"); // Define o nome da janela
        appTitle.getStyleClass().add("app-title"); // Define o Label do Login como o nome da janela

        // Campo de e-mail
        TextField emailField = new TextField(); // Cria uma caixa para capturar email do usuário
        emailField.setPromptText("Email"); // Define o texto inicial dentro da caixa
        emailField.getStyleClass().add("text-field"); // Define o estilo do css para text-field

        // Campo de texto para senha com botão de mostrar/ocultar dentro
        PasswordField passwordField = new PasswordField(); // Cria uma caixa para capturar senha do usuário
        passwordField.setPromptText("Senha");
        passwordField.getStyleClass().add("text-field");

        TextField showPasswordField = new TextField(); // Cria uma caixa para capturar senha porém mostrando ao usuário
        showPasswordField.setManaged(false); // Seta o textfield como não aparente ao usuário
        showPasswordField.setVisible(false); // Seta a visibilidade do textfield como invisível
        showPasswordField.getStyleClass().add("text-field");

        Button togglePasswordButton = new Button("👁"); // Define o símbolo do botão de mostrar senha
        togglePasswordButton.getStyleClass().add("toggle-button");
        togglePasswordButton.setOnAction(e -> { // Inicia uma ação ao clicar no botão
            if (passwordField.isVisible()) { // Se o textfield da senha for visível
                showPasswordField.setText(passwordField.getText());
                showPasswordField.setVisible(true);
                showPasswordField.setManaged(true);
                passwordField.setVisible(false);
                passwordField.setManaged(false);
            } else {
                passwordField.setText(showPasswordField.getText());
                passwordField.setVisible(true);
                passwordField.setManaged(true);
                showPasswordField.setVisible(false);
                showPasswordField.setManaged(false);
            }
        });

        // Usando StackPane para sobrepor o botão dentro do PasswordField
        StackPane passwordStack = new StackPane();
        passwordStack.getChildren().addAll(passwordField, showPasswordField, togglePasswordButton);
        passwordStack.setAlignment(Pos.CENTER_RIGHT);

        // Usando HBox para agrupar o campo de senha
        HBox passwordFieldContainer = new HBox(10, passwordStack);
        passwordFieldContainer.getStyleClass().add("text-field-container");

        // Alinhando o conteúdo do contêiner à direita (se necessário)
        passwordFieldContainer.setAlignment(Pos.CENTER_LEFT);

        // Botão de Login com interação
        Button btnLogin = new Button("Login");
        btnLogin.getStyleClass().add("login-button");
        btnLogin.setOnAction(e -> {
            String email = emailField.getText();
            String password = passwordField.getText();
            if (email.isEmpty() || password.isEmpty()) {
                showAlert("Erro", "Preencha todos os campos", Alert.AlertType.ERROR);
            } else {
                // Simula um login
                System.out.println("Login realizado com sucesso!");
                showAlert("Sucesso", "Você foi autenticado com sucesso!", Alert.AlertType.INFORMATION);
            }
        });

        // Linha separadora com "ou"
        Label orLabel = new Label("ou");
        orLabel.getStyleClass().add("separator-label");

        Separator separator = new Separator();
        separator.getStyleClass().add("separator");

        VBox orContainer = new VBox(orLabel, separator);
        orContainer.setAlignment(Pos.CENTER);

        // Botão "Entrar com o Google" com interação
        Button btnGoogle = new Button("Entrar com o Google");
        btnGoogle.getStyleClass().add("google-button");
        btnGoogle.setOnAction(e -> {
            System.out.println("Redirecionando para o login do Google...");
            showAlert("Login Google", "Você será redirecionado para o Google.", Alert.AlertType.INFORMATION);
        });

        // Botão "Esqueci a senha" com interação
        Button btnForgotPassword = new Button("Esqueci a senha");
        btnForgotPassword.getStyleClass().add("forgot-password-button");
        btnForgotPassword.setOnAction(e -> {
            System.out.println("Redirecionando para a recuperação de senha...");
            showAlert("Recuperação de Senha", "Redirecionando para o processo de recuperação de senha.", Alert.AlertType.INFORMATION);
        });

        // Botão "Criar Conta" com interação
        Button btnCreateAccount = new Button("Criar Conta");
        btnCreateAccount.getStyleClass().add("create-account-button");
        btnCreateAccount.setOnAction(e -> {
            System.out.println("Abrindo tela de criação de conta...");
            showAlert("Criar Conta", "Você será redirecionado para a criação de conta.", Alert.AlertType.INFORMATION);
        });

        // Layout principal (dentro do segundo background)
        VBox formLayout = new VBox(15, appTitle, emailField, passwordFieldContainer, btnLogin, orContainer, btnGoogle, btnForgotPassword, btnCreateAccount);
        formLayout.setAlignment(Pos.CENTER);

        // Segundo background
        VBox highlightedBackground = new VBox(formLayout);
        highlightedBackground.getStyleClass().add("highlighted-background");
        highlightedBackground.setAlignment(Pos.CENTER);
        highlightedBackground.setPadding(new Insets(20));

        // Layout externo com botão de criar conta
        VBox mainLayout = new VBox(20, highlightedBackground);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setPadding(new Insets(30));

        // Fundo principal
        StackPane root = new StackPane();
        root.getStyleClass().add("root-background");
        root.getChildren().add(mainLayout);

        // Cena
        Scene scene = new Scene(root, 400, 600);

        // Adicionar CSS
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        // Configurar Stage
        primaryStage.setTitle("MusicApp");
        primaryStage.getIcons().add(new Image("file:icon.png"));  // Definir um ícone para a janela
        primaryStage.setScene(scene);

// Definir tamanho mínimo e máximo
        primaryStage.setMinWidth(400); // Tamanho mínimo da largura
        primaryStage.setMinHeight(600); // Tamanho mínimo da altura
        primaryStage.setMaximized(true);  // A janela abre maximizada
        Screen screen = Screen.getPrimary();  // Obtém o monitor principal

// Definir a abertura para o monitor principal
        Rectangle2D bounds = screen.getVisualBounds();
        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());

// Garantir que o botão de sair da tela cheia seja invisível
        primaryStage.setFullScreenExitHint("");

// Evento para alternar entre tela cheia ao pressionar F11
        scene.setOnKeyPressed(event -> {
            if (event.getCode().toString().equals("F11")) {
                if (primaryStage.isFullScreen()) {
                    primaryStage.setFullScreen(false); // Desativa o modo de tela cheia
                } else {
                    primaryStage.setFullScreen(true); // Ativa o modo de tela cheia
                }
            }
        });
// Evento de confirmação de encerramento.
        primaryStage.setOnCloseRequest(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Fechar Aplicativo");
            alert.setHeaderText("Tem certeza que quer sair?");
            alert.setContentText("Você pode perder dados não salvos.");
            if (alert.showAndWait().get() != ButtonType.OK) {
                event.consume();  // Impede o fechamento se o usuário cancelar
            }
        });


        primaryStage.show();
    }

    // Função para mostrar alertas
    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
