//imports do javafx
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;

// Classe de inicialização do Stage
public class MusicApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        Scene loginScene = createLoginScene(primaryStage);
        primaryStage.setScene(loginScene);

        // Configurações do Stage (Scene)
        primaryStage.setTitle("MusicApp");
        primaryStage.getIcons().add(new Image("file:icon.png"));
        primaryStage.setMinWidth(400);
        primaryStage.setMinHeight(600);
        primaryStage.setMaximized(true);
        Screen screen = Screen.getPrimary();

        // Definir a abertura para o monitor principal
        Rectangle2D bounds = screen.getVisualBounds();
        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());

        // Garantir que o botão de sair da tela cheia seja invisível
        primaryStage.setFullScreenExitHint("");

        // Evento para alternar entre tela cheia ao pressionar F11
        loginScene.setOnKeyPressed(event -> {
            if (event.getCode().toString().equals("F11")) {
                if (primaryStage.isFullScreen()) {
                    primaryStage.setFullScreen(false);
                } else {
                    primaryStage.setFullScreen(true);
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
                event.consume();
            }
        });

        primaryStage.show();
    }

    // Fim de configurações do Stage (Scene)



    // Classe do Login (Secene)
    private Scene createLoginScene(Stage primaryStage) {
        // Título do app
        Label appTitle = new Label("Music Center");
        appTitle.getStyleClass().add("app-title");

        // Campo de e-mail
        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        emailField.getStyleClass().add("text-field_login");

        // Campo de texto para senha com botão de mostrar/ocultar dentro
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Senha");
        passwordField.getStyleClass().add("text-field_login");

        TextField showPasswordField = new TextField();
        showPasswordField.setManaged(false);
        showPasswordField.setVisible(false);
        showPasswordField.getStyleClass().add("text-field_login");

        Button togglePasswordButton = new Button("👁");
        togglePasswordButton.getStyleClass().add("toggle-button");
        togglePasswordButton.setOnAction(e -> {
            if (passwordField.isVisible()) {
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
                System.out.println("Login realizado com sucesso!");
                showAlert("Sucesso", "Você foi autenticado com sucesso!", Alert.AlertType.INFORMATION);
                Scene homeScene = createHomeScene(primaryStage);
                primaryStage.setScene(homeScene);
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
            Scene createAccountScene = createAccountScene(primaryStage);
            primaryStage.setScene(createAccountScene);
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

        Scene scene = new Scene(root, 400, 600);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        return scene;
    }
    // Fim do Login (Scene)

    // Classe do Cadastro (Scene)
    private Scene createAccountScene(Stage primaryStage) {
        // Título do app
        Label createAccountTitle = new Label("Criar Conta");
        createAccountTitle.getStyleClass().add("app-title");

        // Campos de entrada
        TextField nameField = new TextField();
        nameField.setPromptText("Nome");
        nameField.getStyleClass().add("text-field_login");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        emailField.getStyleClass().add("text-field_login");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Senha");
        passwordField.getStyleClass().add("text-field_login");

        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Confirmar Senha");
        confirmPasswordField.getStyleClass().add("text-field_login");

        // Botão de criar conta
        Button btnSubmit = new Button("Criar Conta");
        btnSubmit.getStyleClass().add("login-button");
        btnSubmit.setOnAction(e -> {
            String name = nameField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();
            String confirmPassword = confirmPasswordField.getText();

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                showAlert("Erro", "Preencha todos os campos", Alert.AlertType.ERROR);
            } else if (!password.equals(confirmPassword)) {
                showAlert("Erro", "As senhas não coincidem", Alert.AlertType.ERROR);
            } else {
                showAlert("Sucesso", "Conta criada com sucesso!", Alert.AlertType.INFORMATION);
                primaryStage.setScene(createLoginScene(primaryStage));
            }
        });

        // Botão de voltar
        Button btnBack = new Button("Voltar");
        btnBack.getStyleClass().add("create-account-button"); // Mesmo estilo do botão de login
        btnBack.setOnAction(e -> primaryStage.setScene(createLoginScene(primaryStage)));

        // Layout principal (dentro do segundo background)
        VBox formLayout = new VBox(15, createAccountTitle, nameField, emailField, passwordField, confirmPasswordField, btnSubmit, btnBack);
        formLayout.setAlignment(Pos.CENTER);
        formLayout.setPadding(new Insets(20));

        // Segundo background
        VBox highlightedBackground = new VBox(formLayout);
        highlightedBackground.getStyleClass().add("highlighted-background");
        highlightedBackground.setAlignment(Pos.CENTER);
        highlightedBackground.setPadding(new Insets(20));

        // Fundo principal
        StackPane root = new StackPane();
        root.getStyleClass().add("root-background");
        root.getChildren().add(highlightedBackground);

        Scene scene = new Scene(root, 400, 600);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        return scene;
    }
    // Fim do Cadastro (Scene)



    // Classe do Home (Scene)
    private Scene createHomeScene(Stage primaryStage) {
        // Layout principal
        BorderPane root = new BorderPane();

        // Barra lateral (Histórico)
        VBox sidebar = new VBox(10); // Uma caixa manipulável estilo div
        sidebar.setAlignment(Pos.TOP_LEFT);
        sidebar.getStyleClass().add("sidebar"); // estilo da barra lateral

        Label historyTitle = new Label("Histórico"); // novo título chamado histórico
        historyTitle.getStyleClass().add("sidebar-title"); // estilo da barra de título

        VBox historyList = new VBox(5);
        historyList.getStyleClass().add("history-list");  // Adiciona a classe para o CSS

        for (int i = 1; i <= 8; i++) {
            // Criar botão com a imagem do artista
            Button artistButton = new Button();
            artistButton.setGraphic(new ImageView(new Image("file:artista.png")));
            artistButton.getStyleClass().add("sidebar-artist-button");

            // Definir estilo e tamanho da imagem no botão
            ImageView imageView = (ImageView) artistButton.getGraphic();
            imageView.setFitWidth(40); // Largura da imagem
            imageView.setFitHeight(40); // Altura da imagem

            // Definir ação ao clicar no botão (exemplo de saída no console)
            int finalI = i;
            artistButton.setOnAction(e -> System.out.println("Você clicou na Música " + finalI));

            // Adicionar botão estilizado à lista de histórico
            historyList.getChildren().add(artistButton);

            // Criar texto do botão
            Label musicLabel = new Label("Música " + i + "\nArtista");
            musicLabel.getStyleClass().add("sidebar-music-label");

            // Combinar imagem e texto no botão usando HBox
            HBox artistButtonContent = new HBox(10, artistButton, musicLabel);
            artistButtonContent.setAlignment(Pos.CENTER_LEFT);

            // Criar o botão estilizado
            Button musicButton = new Button();
            musicButton.getStyleClass().add("sidebar-artist-button");
            musicButton.setGraphic(artistButtonContent); // Define o conteúdo do botão

            // Adicionar o botão à lista de histórico
            historyList.getChildren().add(musicButton);
        }

        Button settingsButton = new Button("Configuração");
        Button exitButton = new Button("Sair");
        settingsButton.getStyleClass().add("sidebar-footer-button");
        exitButton.getStyleClass().add("sidebar-footer-button");

        // Botões agora são parte da sidebar
        Button premiumButton = new Button("Premium");
        premiumButton.getStyleClass().add("sidebar-button"); // Mover premium para a sidebar
        sidebar.getChildren().addAll(premiumButton, historyTitle, historyList, settingsButton, exitButton);

        // Conteúdo central
        VBox contentArea = new VBox(15);
        contentArea.getStyleClass().add("content-area");

        Label chooseArtist = new Label("Escolha seu artista");
        chooseArtist.getStyleClass().add("content-title");

        // Alinhar os botões Curtidas, Biblioteca e Pesquisar em uma linha (HBox)
        HBox topRow = new HBox(15);
        topRow.getStyleClass().add("top-row");

        // Adicionando o campo de busca (searchField) como primeiro item
        TextField searchField = new TextField();
        searchField.setPromptText("Pesquisar...");
        searchField.getStyleClass().add("search-field");

        // Botões para Curtidas, Biblioteca e Pesquisar
        Button searchButton = new Button();
        searchButton.getStyleClass().add("search-button");

        // Caminho da imagem
        ImageView searchIcon = new ImageView(new Image(getClass().getResource("/images/icons/search.png").toExternalForm()));
        searchIcon.setFitWidth(40);  // Definir largura do ícone
        searchIcon.setFitHeight(40);  // Definir altura do ícone

        searchButton.setGraphic(searchIcon);  // Define o ícone no botão


        Button curtirButton = new Button("Curtidas");
        Label bar = new Label("|"); // novo título chamado histórico
        bar.getStyleClass().add("top_bar_text"); // estilo da barra de título
        Button bibliotecaButton = new Button("Biblioteca");
        curtirButton.getStyleClass().add("user-name");

        bibliotecaButton.getStyleClass().add("user-name");

        // Adicionar a informação do perfil à direita da barra
        HBox topBarWithProfile = new HBox(15);
        topBarWithProfile.setAlignment(Pos.CENTER_RIGHT);  // Alinhar à direita
        topBarWithProfile.getStyleClass().add("top-bar-profile");  // Adiciona a classe CSS para o padding
        Button userButton = new Button();
        userButton.getStyleClass().add("user-button");

        ImageView UserIcon = new ImageView(new Image(getClass().getResource("/images/icons/User.png").toExternalForm()));
        UserIcon.setFitWidth(50);  // Definir largura do ícone
        UserIcon.setFitHeight(50);  // Definir altura do ícone

        userButton.setGraphic(UserIcon);  // Define o ícone no botão

        Button userName = new Button("Usuário");
        userName.getStyleClass().add("user-name");
        topBarWithProfile.getChildren().addAll(curtirButton, bar, bibliotecaButton, userButton, userName);

        // Adicionar todos os elementos ao topRow
        topRow.getChildren().addAll(searchField, searchButton);

        // Colocar a barra de perfil à direita
        topRow.getChildren().add(topBarWithProfile);

        HBox artistRow = new HBox(15);
        artistRow.getStyleClass().add("artist-row");
        for (int i = 0; i < 7; i++) {
            Button artistButton = new Button();
            artistButton.getStyleClass().add("artist-button");
            artistRow.getChildren().add(artistButton);
        }

        contentArea.getChildren().addAll(topRow, chooseArtist, artistRow);

        // Player inferior
        HBox musicPlayer = new HBox(10);
        musicPlayer.getStyleClass().add("music-player");

        String[] icons = {"⟳", "⏮", "▶", "⏭", "❤", "⇅"};
        for (String icon : icons) {
            Button playerButton = new Button(icon);
            playerButton.getStyleClass().add("player-button");
            musicPlayer.getChildren().add(playerButton);
        }

        // Configurando os elementos na tela
        root.setLeft(sidebar);  // Colocando a sidebar na esquerda
        root.setCenter(contentArea); // Conteúdo central
        root.setBottom(musicPlayer);  // Player inferior

        Scene scene = new Scene(root, 1000, 600);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        return scene;
    }











    // Função para mostrar alertas
    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    // Fim Da função para mostrar alertas

    // Main para inicialização do programa
    public static void main(String[] args) {
        launch(args);
    }
    // Fim da main
}
