package com.example.apopdm;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class QrCodeScannerActivity extends AppCompatActivity {

    private final ActivityResultLauncher<ScanOptions> scanContract = registerForActivityResult(new ScanContract(), result -> {
        if (result.getContents() != null) {

            String cityName = result.getContents();
            Toast.makeText(QrCodeScannerActivity.this, "Cidade trocada para: " + cityName, Toast.LENGTH_SHORT).show();
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code_scanner);
    }

    @Override
    protected void onResume() {
        super.onResume();

        ScanOptions options = new ScanOptions();
        options.setPrompt("Escaneie o QR Code para trocar a cidade");
        options.setBeepEnabled(true);
        options.setOrientationLocked(false);
        scanContract.launch(options);
    }
}
