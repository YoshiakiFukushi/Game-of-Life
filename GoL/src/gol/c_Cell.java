package gol;

import java.awt.Color;
import java.awt.Graphics;

public class c_Cell
{
	private int x;
	private int y;//(x,y)の位置座標
	private boolean alive;//生死判定
	private boolean nextStage;
	static  int size = 10;//cellのサイズを設定している
	public c_Cell(int x,int y) {//インスタンス
		this.x = x;
		this.y = y;
	}
	public boolean getAlive()//以下二つの関数は初期状態状態および現在状態を示す(セッター・ゲッター)
	{//ゲッター
		return alive;
	}
	public void setAlive(boolean getalive)
	{//セッター
		this.alive = getalive;
	}
	public void setNextStage(boolean getNextStage)//こっからは次の状態に移行する際に使う型
	{//セッター
		this.nextStage = getNextStage;
	}
	public void NextStage()
	{//上書き(NextStageのゲッターは使わない)
		alive = nextStage;
	}
	public void draw(Graphics g)
	{
		g.setColor(Color.ORANGE);//グラフィックス・コンテキストの現在の色を、指定された色に設定します。
		g.drawRect(x * size,y*size,size,size);//グリッドを書く
		if(alive) g.setColor(Color.ORANGE);//もし生きていたら塗りつぶす
		else g.setColor(Color.WHITE);//それ以外なら白くする
		g.fillRect(x * size+1, y*size+1, size-1, size-1);//指定された矩形の輪郭を描きます。矩形の左端と右端はxとx + widthにあります。上端と下端はy
	}
}
