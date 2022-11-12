package ru.touchin.push.message.provider.hpk.clients.hms.enums

import ru.touchin.push.message.provider.hpk.base.enums.ValueableSerializableEnum

internal enum class HmsResponseCode(
    override val value: Int,
    val description: String,
) : ValueableSerializableEnum<Int> {

    UNKNOWN(-1, "Unknown"),
    INVALID_CLIENT_SECRET(1101, "Invalid client_secret: app or server has mismatching credentials"),
    SUCCESS(80000000, "Success"),
    SOME_TOKENS_ARE_INVALID(80100000, "Some tokens are right, the others are illegal"),
    PARAMETERS_ARE_INVALID(80100001, "Parameters check error"),
    PUSH_TOKEN_NOT_SPECIFIED(80100002, "Token number should be one when send sys message"),
    INCORRECT_MESSAGE_STRUCTURE(80100003, "Incorrect message structure"),
    TTL_IS_INVALID(80100004, "TTL is less than current time, please check"),
    COLLAPSE_KEY_IS_INVALID(80100013, "Collapse_key is illegal, please check"),
    MESSAGE_DATA_IS_VULNERABLE(80100016, "Message contains sensitive information, please check"),
    TOPIC_AMOUNT_EXCEEDED(80100017, "A maximum of 100 topic-based messages can be sent at the same time"),
    INVALID_MESSAGE_BODY(80100018, "Invalid message body"),
    OAUTH_AUTHENTICATION_ERROR(80200001, "Oauth authentication error"),
    OAUTH_TOKEN_EXPIRED(80200003, "Oauth Token expired"),
    PERMISSION_DENIED(80300002, "There is no permission to send a message to a specified device"),
    INVALID_TOKEN(80300007, "The specified token is invalid"),
    MESSAGE_SIZE_EXCEEDED(80300008, "The message body size exceeds the default value set by the system (4K)"),
    TOKEN_AMOUNT_EXCEEDED(80300010, "Tokens exceed the default value"),
    MESSAGE_PERMISSION_DENIED(80300011, "No permission to send high-level notification messages"),
    OAUTH_SERVER_ERROR(80600003, "Request OAuth service failed"),
    INTERNAL_SERVER_ERROR(81000001, "System inner error"),
    GROUP_ERROR(82000001, "GroupKey or groupName error"),
    GROUP_MISMATCH(82000002, "GroupKey and groupName do not match"),
    INVALID_TOKEN_ARRAY(82000003, "Token array is null"),
    GROUP_NOT_EXIST(82000004, "Group do not exist"),
    GROUP_APP_MISMATCH(82000005, "Group do not belong to this app"),
    INVALID_TOKEN_ARRAY_OR_GROUP(82000006, "Token array or group number is transfinited"),
    INVALID_TOPIC(82000007, "Invalid topic"),
    TOKEN_AMOUNT_IS_NULL_OR_EXCEEDED(82000008, "Token array null or transfinited"),
    TOO_MANY_TOPICS(82000009, "Topic amount exceeded: at most 2000"),
    SOME_TOKENS_ARE_INCORRECT(82000010, "Some tokens are incorrect"),
    TOKEN_IS_NULL(82000011, "Token is null"),
    DATA_LOCATION_NOT_SPECIFIED(82000012, "Data storage location is not selected"),
    DATA_LOCATION_MISMATCH(82000013, "Data storage location does not match the actual data");

    companion object {

        fun fromCode(code: String): HmsResponseCode {
            return values().find { it.value.toString() == code }
                ?: UNKNOWN
        }

    }

}
