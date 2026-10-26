package com.example.lab5 // Tên package chuẩn Lab 5

import android.os.Bundle
import android.os.SystemClock
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.example.lab5.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // Biến chống click đúp (Debouncing) - Bài 1
    private var lastClickTime: Long = 0L
    private val CLICK_DEBOUNCE_DELAY = 1000L // 1 giây

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Khởi tạo các event cho 3 bài
        setupSubmitButton()        // Bài 1
        setupRealtimeValidation() // Bài 2
        setupChoiceListeners()     // Bài 3
    }

    /**
     * BÀI 1: Bắt sự kiện Click & Chống Click Đúp (Debouncing)
     */
    private fun setupSubmitButton() {
        binding.btnSubmit.setOnClickListener {
            val currentTime = SystemClock.elapsedRealtime()

            if (currentTime - lastClickTime < CLICK_DEBOUNCE_DELAY) {
                return@setOnClickListener
            }
            lastClickTime = currentTime

            val name = binding.etFullName.text.toString().trim()
            Toast.makeText(this, "Đã gửi hồ sơ đăng ký của: $name", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * BÀI 2: Lắng nghe gõ phím thời gian thực (Xóa lỗi khi nhập)
     */
    private fun setupRealtimeValidation() {
        binding.etFullName.doAfterTextChanged {
            if (binding.tilFullName.error != null) {
                binding.tilFullName.error = null
                binding.tilFullName.isErrorEnabled = false
            }
        }

        binding.etEmail.doAfterTextChanged {
            if (binding.tilEmail.error != null) {
                binding.tilEmail.error = null
                binding.tilEmail.isErrorEnabled = false
            }
        }

        binding.etPassword.doAfterTextChanged {
            if (binding.tilPassword.error != null) {
                binding.tilPassword.error = null
                binding.tilPassword.isErrorEnabled = false
            }
        }
    }

    /**
     * BÀI 3: Lắng nghe sự kiện RadioGroup & CheckBox
     */
    private fun setupChoiceListeners() {
        // RadioGroup Giới tính
        binding.rgGender.setOnCheckedChangeListener { _, checkedId ->
            val gender = when (checkedId) {
                R.id.rbMale -> "Nam"
                R.id.rbFemale -> "Nữ"
                R.id.rbOther -> "Khác"
                else -> ""
            }
            Toast.makeText(this, "Giới tính đã chọn: $gender", Toast.LENGTH_SHORT).show()
        }

        // CheckBox Mạng xã hội
        binding.cbFacebook.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Toast.makeText(this, "Đã chọn: Facebook", Toast.LENGTH_SHORT).show()
            }
        }

        binding.cbZalo.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Toast.makeText(this, "Đã chọn: Zalo", Toast.LENGTH_SHORT).show()
            }
        }

        binding.cbTiktok.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Toast.makeText(this, "Đã chọn: TikTok", Toast.LENGTH_SHORT).show()
            }
        }

        // CheckBox Điều khoản điều khiển nút Submit
        binding.cbTerms.setOnCheckedChangeListener { _, isChecked ->
            binding.btnSubmit.isEnabled = isChecked
        }
    }
}