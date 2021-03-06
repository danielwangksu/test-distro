DESCRIPTION = "Install tegra bup payload file in the location expected by nv_update_engine"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

UBOOT_SUFFIX ??= "bin"
UBOOT_IMAGE ?= "u-boot-${MACHINE}.${UBOOT_SUFFIX}"

BUPFILE = "${@'tegra-minimal-initramfs-${MACHINE}.bup-payload' if d.getVar("PREFERRED_PROVIDER_virtual/bootloader").startswith("cboot") else '${UBOOT_IMAGE}.bup-payload'}"

do_install() {
    install -d ${D}/opt/ota_package/
    install -m 0644 ${DEPLOY_DIR_IMAGE}/${BUPFILE} ${D}/opt/ota_package/bl_update_payload
}

do_install[depends] += "${@'tegra-minimal-initramfs:do_image_complete' if d.getVar('PREFERRED_PROVIDER_virtual/bootloader').startswith('cboot') else 'u-boot-bup-payload:do_deploy'}"
FILES_${PN} = "/opt/ota_package/bl_update_payload"
RDEPENDS_${PN} += "tegra-redundant-boot"
PACKAGE_ARCH = "${MACHINE_ARCH}"
