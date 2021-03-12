package gol;

public class Main
{
	static int width;
	static int height;
	public static void main(String[] args)
	{
		a_Frame frame = new a_Frame();//新しいFrame
		frame.setDefaultCloseOperation(3);//がこのフレームの「クローズ」を開始したときに、デフォルトで実行される処理を設定します。
		frame.setVisible(true);//パラメータの値に応じて、このWindowを表示または非表示にします。
		frame.setResizable(false);//フレームのサイズを変更できるかどうかを設定します
		
		width = frame.getWidth();
		height = frame.getHeight();
		frame.createScreen();//a_Frameクラス内のcreateScreen()関数でScreenを作り出す

		long lastFrame = System.currentTimeMillis();//戻り値の時間単位はミリ秒で表される現在の時間を返します。
		while(true)
		{
			long thisFrame = System.currentTimeMillis();
			frame.repaint();//Frameクラス内のコンポーネントscreenを塗り直す
			float tslf = (float)((thisFrame - lastFrame)/1000.0);
			lastFrame = thisFrame;

			frame.update(tslf);

			try
			{
				Thread.sleep(10);//システム・タイマーを前提として、指定されたミリ秒数の間一時的停止させます。
			}catch(InterruptedException e)
			{
				e.printStackTrace();//このスロー可能オブジェクトとそのバックトレースを指定された印刷ストリームに出力します。
			}
		}
	}
}
