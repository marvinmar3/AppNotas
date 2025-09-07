package com.example.appnotas;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.appnotas.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private NotesViewModel viewModel;
    private SharedPreferences prefs;

    private static final String PREFS_NAME = "NotasPrefs";
    private static final String KEY_NOTES = "NotasGuardadas";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // viewBinding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // viewModel
        viewModel = new ViewModelProvider(this).get(NotesViewModel.class);

        // sharedPreferences
        prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        // cargar notas guardadas
        loadNotes();

        // botón Guardar
        binding.btnSave.setOnClickListener(v -> {
            String note = binding.etNote.getText().toString().trim();
            if (!note.isEmpty()) {
                viewModel.addNote(note);
                binding.etNote.setText("");
                updateUI();
                saveNotes();
            }
        });

        // B
        //botón Eliminar todas
        binding.btnClear.setOnClickListener(v -> {
            viewModel.clearNotes();
            updateUI();
            saveNotes();
        });

        // mostrar UI inicial
        updateUI();
    }

    private void updateUI() {
        binding.tvCounter.setText("Notas: " + viewModel.getCount());

        StringBuilder builder = new StringBuilder();
        for (String note : viewModel.getNotes()) {
            builder.append("• ").append(note).append("\n");
        }
        binding.tvNotes.setText(builder.toString());
    }

    private void saveNotes() {
        SharedPreferences.Editor editor = prefs.edit();
        StringBuilder builder = new StringBuilder();
        for (String note : viewModel.getNotes()) {
            builder.append(note).append(";;"); // separador
        }
        editor.putString(KEY_NOTES, builder.toString());
        editor.apply();
    }

    private void loadNotes() {
        String saved = prefs.getString(KEY_NOTES, "");
        if (saved != null && !saved.isEmpty()) {
            String[] array = saved.split(";;");
            for (String note : array) {
                if (!note.trim().isEmpty()) {
                    viewModel.addNote(note);
                }
            }
        }
    }
}