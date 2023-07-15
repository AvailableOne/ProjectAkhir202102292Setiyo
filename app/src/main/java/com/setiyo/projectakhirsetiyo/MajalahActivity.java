package com.setiyo.projectakhirsetiyo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MajalahActivity extends AppCompatActivity {
    EditText judulmajalah, jenis, bahasa, tahun;
    Button simpan, tampil, hapus, edit;
    DBHelper db;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_majalah);

        judulmajalah = findViewById(R.id.judulmajalah);
        jenis = findViewById(R.id.jenis);
        bahasa = findViewById(R.id.bahasa);
        tahun = findViewById(R.id.tahun);
        simpan = findViewById(R.id.simpan);
        tampil = findViewById(R.id.tampil);
        hapus = findViewById(R.id.hapus);
        edit = findViewById(R.id.edit);
        db = new DBHelper(this);

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String jm = judulmajalah.getText().toString();
                String jen = jenis.getText().toString();
                String bah = bahasa.getText().toString();
                String thn = tahun.getText().toString();
                if (TextUtils.isEmpty(jm) || TextUtils.isEmpty(jen) || TextUtils.isEmpty(bah) || TextUtils.isEmpty(thn))
                    Toast.makeText(MajalahActivity.this, "Semua Field Wajib diIsi", Toast.LENGTH_SHORT).show();
                else {
                    if (!jm.equals("")) {
                        Boolean checkkode = db.checkmajalah(jm);
                        if (checkkode == false) {
                            Boolean insert = db.insertDatamajalah(jm, jen, bah, thn);
                            if (insert == true) {
                                Toast.makeText(MajalahActivity.this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MajalahActivity.this, "Data gagal disimpan", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(MajalahActivity.this, "Data Film Sudah Ada", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MajalahActivity.this, "", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
        tampil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = db.tampilDatamajalah();
                if (res.getCount()==0){
                    Toast.makeText(MajalahActivity.this, "Tidak Ada Data", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()){
                    buffer.append("Judul Majalah : "+ res.getString(0) + "\n");
                    buffer.append("Jenis : "+ res.getString(1) + "\n");
                    buffer.append("Bahasa : "+ res.getString(2) + "\n");
                    buffer.append("Tahun : "+ res.getString(3) + "\n");
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(MajalahActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Data Majalah");
                builder.setMessage(buffer.toString());
                builder.show();
            }
        });

        hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String jm = judulmajalah.getText().toString();
                Boolean cekHapusData = db.hapusDatamajalah(jm);
                if (cekHapusData == true)
                    Toast.makeText(MajalahActivity.this, "Data Terhapus", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MajalahActivity.this, "Data Tidak Ada", Toast.LENGTH_SHORT).show();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String jm = judulmajalah.getText().toString();
                String jen = jenis.getText().toString();
                String bah = bahasa.getText().toString();
                String thn = tahun.getText().toString();
                if (TextUtils.isEmpty(jm) || TextUtils.isEmpty(jen) || TextUtils.isEmpty(bah)
                        || TextUtils.isEmpty(thn)) {
                    Toast.makeText(MajalahActivity.this, "Semua Field Wajib Diisi", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean edit = db.editDatamajalah(jm, jen, bah, thn);
                    if (edit == true) {
                        Toast.makeText(MajalahActivity.this, "Data Berhasil Diedit", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MajalahActivity.this, "Data Gagal Diedit", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}