package ru.touchin.auth.core.device.services

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.spy
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import ru.touchin.auth.core.device.dto.Device
import ru.touchin.auth.core.device.exceptions.DeviceNotFoundException
import ru.touchin.auth.core.device.models.DeviceEntity
import ru.touchin.auth.core.device.dto.enums.DevicePlatform
import ru.touchin.auth.core.device.repository.DeviceRepository
import java.util.*

@ActiveProfiles("test")
@SpringBootTest
internal class DeviceCoreServiceImplTest {

    private var deviceRepository: DeviceRepository = mock {}

    private var deviceCoreService: DeviceCoreService = spy(
        DeviceCoreServiceImpl(deviceRepository)
    )

    @Test
    @DisplayName("Device должен создаваться в базе")
    fun deviceShouldBeCreated() {
        val deviceId = UUID.fromString("0d3ded83-02c7-4bae-9cd2-3076e0bce046")
        doAnswer { invocation ->
            (invocation.getArgument(0) as DeviceEntity)
                .apply {
                    id = deviceId
                }
        }.`when`(deviceRepository).save(any())

        val actualDevice = deviceCoreService.create(DevicePlatform.Huawei)

        val expectedDevice = Device(
            id = deviceId,
            platform = DevicePlatform.Huawei,
        )

        assertEquals(expectedDevice, actualDevice)
    }

    @Test
    @DisplayName("Device можно получить через сервис")
    fun deviceShouldBeGet() {
        val deviceId = UUID.fromString("0d3ded83-02c7-4bae-9cd2-3076e0bce046")

        doReturn(
            DeviceEntity()
                .apply {
                    id = deviceId
                    platform = DevicePlatform.Android
                }
                .let { device ->
                    Optional.of(device)
                }
        ).`when`(deviceRepository).findById(deviceId)

        val actualDevice = deviceCoreService.get(deviceId)

        val expectedDevice = Device(
            id = deviceId,
            platform = DevicePlatform.Android,
        )

        assertEquals(expectedDevice, actualDevice)
    }

    @Test
    @DisplayName("Для несуществующих устройств должно быть брошено исключение DeviceNotFoundException")
    fun deviceShouldBeNotFound() {
        val missingDeviceId = UUID.fromString("d3e957df-6686-421e-9807-7128c8e680ea")

        doReturn(
            Optional.empty<DeviceEntity>()
        ).`when`(deviceRepository).findById(any())

        assertThrows<DeviceNotFoundException> {
            deviceCoreService.get(missingDeviceId)
        }
    }

}
