package msku.ceng.madlab.week5;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    static final String PLAYER_1 = "X";
    static final String PLAYER_2 = "O";
    boolean player1Turn = true; 
    byte[][] board = new byte[3][3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Butonları tanımlayın
        Button[][] buttons = new Button[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);

                int finalI = i;
                int finalJ = j;
                buttons[i][j].setOnClickListener(v -> onButtonClick(finalI, finalJ, buttons));
            }
        }
    }
    private void onButtonClick(int row, int col, Button[][] buttons) {
        if (board[row][col] != 0) {
            return; // Geçersiz hamle
        }

        board[row][col] = player1Turn ? (byte) 1 : (byte) 2; // Tahta durumunu güncelle
        buttons[row][col].setText(player1Turn ? PLAYER_1 : PLAYER_2);

        if (checkWin()) {
            disableBoard(buttons);
            Toast.makeText(this, (player1Turn ? "Player 1" : "Player 2") + " wins!", Toast.LENGTH_SHORT).show();
        } else if (isBoardFull()) {
            Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        } else {
            player1Turn = !player1Turn; // Sırayı değiştir
        }
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkWin() {
        for (int i = 0; i < 3; i++) {
            // Satır veya sütun kontrolü
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0] != 0) return true;
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[0][i] != 0) return true;
        }

        // Çapraz kontrolü
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != 0) return true;
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] != 0) return true;

        return false;
    }

    private void disableBoard(Button[][] buttons) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setEnabled(false);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_restart) {
            restartGame();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void restartGame() {
        board = new byte[3][3];
        player1Turn = true;
        recreate();
    }






}

