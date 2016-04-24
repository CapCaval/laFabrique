package org.capcaval.ermine.jfx;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javafx.application.Application;
import javafx.stage.Stage;

public class JfxApplication extends Application {

	private static JfxApplication instance;
	static final CountDownLatch syncro = new CountDownLatch(1);

	public static synchronized void start() {
		if (JfxApplication.instance == null) {
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					JfxApplication.startJavaFx();
				}
			}, "JfxApplication");
			t.start();

			// wait for javaFx environment to be set
			// and ready to use
			try {
				syncro.await(2, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		// do nothing if already started
	}


	public JfxApplication() {
		instance = this;
	}

	@Override
	public void start(Stage stage) throws Exception {
//		this.stage = stage;
//		stage.setTitle("Drawing Operations Test");
//		Group root = new Group();
//		Canvas canvas = new Canvas(300, 250);
//		GraphicsContext gc = canvas.getGraphicsContext2D();
//
//		gc.fillText("Hello!", 20, 20);
//
//		root.getChildren().add(canvas);
//		stage.setScene(new Scene(root));
//		// stage.show();
//
//		Stage stage2 = new Stage();
//		stage2.setX(10);
//		stage2.setY(50);
//		Scene scene = new Scene(new Group(new Button("my second window")));
//		stage2.setScene(scene);
//		stage2.show();

		JfxApplication.syncro.countDown();
	}


	static protected void startJavaFx() {
		launch(new String[] {});
	}
}
