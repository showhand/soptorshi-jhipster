import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { MatTabsModule } from '@angular/material';

@NgModule({
    imports: [
        MatTabsModule,
        RouterModule.forChild([
            {
                path: 'department',
                loadChildren: './department/department.module#SoptorshiDepartmentModule'
            },
            {
                path: 'designation',
                loadChildren: './designation/designation.module#SoptorshiDesignationModule'
            },
            {
                path: 'employee',
                loadChildren: './employee/employee.module#SoptorshiEmployeeModule'
            },
            {
                path: 'attachment',
                loadChildren: './attachment/attachment.module#SoptorshiAttachmentModule'
            },
            {
                path: 'academic-information',
                loadChildren: './academic-information/academic-information.module#SoptorshiAcademicInformationModule'
            },
            {
                path: 'training-information',
                loadChildren: './training-information/training-information.module#SoptorshiTrainingInformationModule'
            },
            {
                path: 'family-information',
                loadChildren: './family-information/family-information.module#SoptorshiFamilyInformationModule'
            },
            {
                path: 'reference-information',
                loadChildren: './reference-information/reference-information.module#SoptorshiReferenceInformationModule'
            },
            {
                path: 'experience-information',
                loadChildren: './experience-information/experience-information.module#SoptorshiExperienceInformationModule'
            },
            {
                path: 'department',
                loadChildren: './department/department.module#SoptorshiDepartmentModule'
            },
            {
                path: 'designation',
                loadChildren: './designation/designation.module#SoptorshiDesignationModule'
            },
            {
                path: 'attachment',
                loadChildren: './attachment/attachment.module#SoptorshiAttachmentModule'
            },

            {
                path: 'training-information',
                loadChildren: './training-information/training-information.module#SoptorshiTrainingInformationModule'
            },
            {
                path: 'family-information',
                loadChildren: './family-information/family-information.module#SoptorshiFamilyInformationModule'
            },
            {
                path: 'reference-information',
                loadChildren: './reference-information/reference-information.module#SoptorshiReferenceInformationModule'
            },
            {
                path: 'experience-information',
                loadChildren: './experience-information/experience-information.module#SoptorshiExperienceInformationModule'
            },
            {
                path: 'attachment',
                loadChildren: './attachment/attachment.module#SoptorshiAttachmentModule'
            },
            {
                path: 'academic-information-attachment',
                loadChildren:
                    './academic-information-attachment/academic-information-attachment.module#SoptorshiAcademicInformationAttachmentModule'
            },
            {
                path: 'experience-information-attachment',
                loadChildren:
                    './experience-information-attachment/experience-information-attachment.module#SoptorshiExperienceInformationAttachmentModule'
            },
            {
                path: 'training-information-attachment',
                loadChildren:
                    './training-information-attachment/training-information-attachment.module#SoptorshiTrainingInformationAttachmentModule'
            },
            {
                path: 'employee',
                loadChildren: './employee/employee.module#SoptorshiEmployeeModule'
            },

            {
                path: 'training-information',
                loadChildren: './training-information/training-information.module#SoptorshiTrainingInformationModule'
            },
            {
                path: 'experience-information',
                loadChildren: './experience-information/experience-information.module#SoptorshiExperienceInformationModule'
            },
            {
                path: 'employee',
                loadChildren: './employee/employee.module#SoptorshiEmployeeModule'
            },
            {
                path: 'office',
                loadChildren: './office/office.module#SoptorshiOfficeModule'
            },
            {
                path: 'financial-account-year',
                loadChildren:
                    './financial-account-year-extended/financial-account-year-extended.module#SoptorshiFinancialAccountYearExtendedModule'
            },
            {
                path: 'tax',
                loadChildren: './tax/tax.module#SoptorshiTaxModule'
            },
            {
                path: 'salary',
                loadChildren: './salary-extended/salary-extended.module#SoptorshiSalaryModule'
            },
            {
                path: 'provident-fund',
                loadChildren: './provident-fund/provident-fund.module#SoptorshiProvidentFundModule'
            },
            {
                path: 'monthly-salary',
                loadChildren: './monthly-salary-extended/monthly-salary-extended.module#SoptorshiMonthlySalaryExtendedModule'
            },
            {
                path: 'advance',
                loadChildren: './advance/advance.module#SoptorshiAdvanceModule'
            },
            {
                path: 'fine',
                loadChildren: './fine/fine.module#SoptorshiFineModule'
            },
            {
                path: 'loan',
                loadChildren: './loan/loan.module#SoptorshiLoanModule'
            },
            {
                path: 'holiday-type',
                loadChildren: './holiday-type-extended/holiday-type-extended.module#SoptorshiHolidayTypeExtendedModule'
            },
            {
                path: 'holiday',
                loadChildren: './holiday-extended/holiday-extended.module#SoptorshiHolidayExtendedModule'
            },
            {
                path: 'employee',
                loadChildren: './employee/employee.module#SoptorshiEmployeeModule'
            },
            {
                path: 'tax',
                loadChildren: './tax/tax.module#SoptorshiTaxModule'
            },
            {
                path: 'salary',
                loadChildren: './salary/salary.module#SoptorshiSalaryModule'
            },
            {
                path: 'monthly-salary',
                loadChildren: './monthly-salary/monthly-salary.module#SoptorshiMonthlySalaryModule'
            },
            {
                path: 'payroll-management',
                loadChildren: './payroll-management/payroll-management.module#SoptorshiPayrollManagementModule'
            },
            {
                path: 'tax',
                loadChildren: './tax/tax.module#SoptorshiTaxModule'
            },
            {
                path: 'designation-wise-allowance',
                loadChildren:
                    './designation-wise-allowance-extended/designation-wise-allowance-extended.module#SoptorshiDesignationWiseAllowanceExtendedModule'
            },
            {
                path: 'provident-fund',
                loadChildren: './provident-fund/provident-fund.module#SoptorshiProvidentFundModule'
            },
            {
                path: 'monthly-salary',
                loadChildren: './monthly-salary/monthly-salary.module#SoptorshiMonthlySalaryModule'
            },
            {
                path: 'advance',
                loadChildren: './advance/advance.module#SoptorshiAdvanceModule'
            },
            {
                path: 'fine',
                loadChildren: './fine/fine.module#SoptorshiFineModule'
            },
            {
                path: 'loan',
                loadChildren: './loan/loan.module#SoptorshiLoanModule'
            },
            {
                path: 'fine-management',
                loadChildren: './fine-management/fine-management.module#SoptorshiFineManagementModule'
            },
            {
                path: 'advance-management',
                loadChildren: './advance-management/advance-management.module#SoptorshiAdvanceManagementModule'
            },
            {
                path: 'loan-management',
                loadChildren: './loan-management/loan-management.module#SoptorshiLoanManagementModule'
            },
            {
                path: 'provident-management',
                loadChildren: './provident-management/provident-management.module#SoptorshiProvidentManagementModule'
            },
            {
                path: 'allowance-management',
                loadChildren: './allowance-management/allowance-management.module#SoptorshiAllowanceManagementModule'
            },
            {
                path: 'salary',
                loadChildren: './salary/salary.module#SoptorshiSalaryModule'
            },
            {
                path: 'fine',
                loadChildren: './fine/fine.module#SoptorshiFineModule'
            },
            {
                path: 'fine',
                loadChildren: './fine/fine.module#SoptorshiFineModule'
            },
            {
                path: 'advance',
                loadChildren: './advance/advance.module#SoptorshiAdvanceModule'
            },
            {
                path: 'fine',
                loadChildren: './fine/fine.module#SoptorshiFineModule'
            },
            {
                path: 'loan',
                loadChildren: './loan/loan.module#SoptorshiLoanModule'
            },
            {
                path: 'fine-advance-loan-management',
                loadChildren: './fine-advance-loan-management/fine-advance-loan-management.module#SoptorshiFineAdvanceLoanManagementModule'
            },
            {
                path: 'employee',
                loadChildren: './employee/employee.module#SoptorshiEmployeeModule'
            },
            {
                path: 'manager',
                loadChildren: './manager/manager.module#SoptorshiManagerModule'
            },
            {
                path: 'manager',
                loadChildren: './manager/manager.module#SoptorshiManagerModule'
            },
            {
                path: 'provident-fund',
                loadChildren: './provident-fund/provident-fund.module#SoptorshiProvidentFundModule'
            },
            {
                path: 'monthly-salary',
                loadChildren: './monthly-salary/monthly-salary.module#SoptorshiMonthlySalaryModule'
            },
            {
                path: 'special-allowance-time-line',
                loadChildren: './special-allowance-time-line/special-allowance-time-line.module#SoptorshiSpecialAllowanceTimeLineModule'
            },
            {
                path: 'monthly-salary',
                loadChildren: './monthly-salary/monthly-salary.module#SoptorshiMonthlySalaryModule'
            },
            {
                path: 'provident-fund',
                loadChildren: './provident-fund/provident-fund.module#SoptorshiProvidentFundModule'
            },
            {
                path: 'advance',
                loadChildren: './advance/advance.module#SoptorshiAdvanceModule'
            },
            {
                path: 'leave-type',
                loadChildren: './leave-type-extended/leave-type-extended.module#SoptorshiLeaveTypeExtendedModule'
            },
            {
                path: 'leave-application',
                loadChildren: './leave-application-extended/leave-application-extended.module#SoptorshiLeaveApplicationExtendedModule'
            },
            {
                path: 'leave-attachment',
                loadChildren: './leave-attachment-extended/leave-attachment-extended.module#SoptorshiLeaveAttachmentExtendedModule'
            },
            {
                path: 'leave-balance',
                loadChildren: './leave-balance/leave-balance.module#SoptorshiLeaveBalanceModule'
            },
            {
                path: 'department',
                loadChildren: './department/department.module#SoptorshiDepartmentModule'
            },
            {
                path: 'purchase-committee',
                loadChildren: './purchase-committee-extended/purchase-committee-extended.module#SoptorshiPurchaseCommitteeExtendedModule'
            },
            {
                path: 'vendor',
                loadChildren: './vendor-extended/vendor-extended.module#SoptorshiVendorExtendedModule'
            },
            {
                path: 'department',
                loadChildren: './department/department.module#SoptorshiDepartmentModule'
            },
            {
                path: 'bill',
                loadChildren: './bill/bill.module#SoptorshiBillModule'
            },
            {
                path: 'tax',
                loadChildren: './tax/tax.module#SoptorshiTaxModule'
            },
            {
                path: 'monthly-salary',
                loadChildren: './monthly-salary/monthly-salary.module#SoptorshiMonthlySalaryModule'
            },
            {
                path: 'monthly-salary',
                loadChildren: './monthly-salary/monthly-salary.module#SoptorshiMonthlySalaryModule'
            },
            {
                path: 'monthly-salary',
                loadChildren: './monthly-salary/monthly-salary.module#SoptorshiMonthlySalaryModule'
            },
            {
                path: 'department-head',
                loadChildren: './department-head-extended/department-head-extended.module#SoptorshiDepartmentHeadExtendedModule'
            },
            {
                path: 'budget-allocation',
                loadChildren: './budget-allocation-extended/budget-allocation-extended.module#SoptorshiBudgetAllocationExtendedModule'
            },
            {
                path: 'financial-account-year',
                loadChildren: './financial-account-year/financial-account-year.module#SoptorshiFinancialAccountYearModule'
            },
            {
                path: 'product-category',
                loadChildren: './product-category-extended/product-category-extended.module#SoptorshiProductCategoryExtendedModule'
            },
            {
                path: 'product',
                loadChildren: './product-extended/product-extended.module#SoptorshiProductExtendedModule'
            },
            {
                path: 'product-price',
                loadChildren: './product-price/product-price.module#SoptorshiProductPriceModule'
            },
            {
                path: 'requisition',
                loadChildren: './requisition-extended/requisition-extended.module#SoptorshiRequisitionExtendedModule'
            },
            {
                path: 'requisition-details',
                loadChildren: './requisition-details-extended/requisition-details-extended.module#SoptorshiRequisitionDetailsExtendedModule'
            },
            {
                path: 'quotation',
                loadChildren: './quotation-extended/quotation-extended.module#SoptorshiQuotationExtendedModule'
            },
            {
                path: 'quotation-details',
                loadChildren: './quotation-details-extended/quotation-details-extended.module#SoptorshiQuotationDetailsExtendedModule'
            },
            {
                path: 'work-order',
                loadChildren: './work-order/work-order.module#SoptorshiWorkOrderModule'
            },
            {
                path: 'terms-and-conditions',
                loadChildren: './terms-and-conditions-extended/terms-and-conditions-extended.module#SoptorshiTermsAndConditionsModule'
            },

            {
                path: 'vendor-contact-person',
                loadChildren:
                    './vendor-contact-person-extended/vendor-contact-person-extended.module#SoptorshiVendorContactPersonExtendedModule'
            },
            {
                path: 'purchase-order',
                loadChildren: './purchase-order-extended/purchase-order-extended.module#SoptorshiPurchaseOrderExtendedModule'
            },

            {
                path: 'attendance-excel-upload',
                loadChildren: './attendance-excel-upload/attendance-excel-upload.module#SoptorshiAttendanceExcelUploadModuleExtended'
            },
            {
                path: 'attendance',
                loadChildren: './attendance-extended/attendance-extended.module#SoptorshiAttendanceExtendedModule'
            },
            {
                path: 'manufacturer',
                loadChildren: './manufacturer-extended/manufacturer-extended.module#SoptorshiManufacturerExtendedModule'
            },
            {
                path: 'item-category',
                loadChildren: './item-category-extended/item-category-extended.module#SoptorshiItemCategoryExtendedModule'
            },
            {
                path: 'item-sub-category',
                loadChildren: './item-sub-category-extended/item-sub-category-extended.module#SoptorshiItemSubCategoryExtendedModule'
            },
            {
                path: 'inventory-location',
                loadChildren: './inventory-location-extended/inventory-location-extended.module#SoptorshiInventoryLocationExtendedModule'
            },
            {
                path: 'inventory-sub-location',
                loadChildren:
                    './inventory-sub-location-extended/inventory-sub-location-extended.module#SoptorshiInventorySubLocationExtendedModule'
            },
            {
                path: 'stock-in-process',
                loadChildren: './stock-in-process-extended/stock-in-process-extended.module#SoptorshiStockInProcessExtendedModule'
            },
            {
                path: 'stock-in-item',
                loadChildren: './stock-in-item-extended/stock-in-item-extended.module#SoptorshiStockInItemExtendedModule'
            },
            {
                path: 'stock-out-item',
                loadChildren: './stock-out-item-extended/stock-out-item-extended.module#SoptorshiStockOutItemExtendedModule'
            },
            {
                path: 'stock-status',
                loadChildren: './stock-status-extended/stock-status-extended.module#SoptorshiStockStatusExtendedModule'
            },
            {
                path: 'vendor',
                loadChildren: './vendor/vendor.module#SoptorshiVendorModule'
            },
            {
                path: 'voucher',
                loadChildren: './voucher/voucher.module#SoptorshiVoucherModule'
            },
            {
                path: 'voucher-number-control',
                loadChildren: './voucher-number-control-extended/voucher-number-control-extended.module#SoptorshiVoucherNumberControlModule'
            },
            {
                path: 'period-close',
                loadChildren: './period-close-extended/period-close-extended.module#SoptorshiPeriodCloseModule'
            },
            {
                path: 'currency',
                loadChildren: './currency-extended/currency-extended.module#SoptorshiCurrencyModule'
            },
            {
                path: 'mst-group',
                loadChildren: './mst-group-extended/mst-group-extended.module#SoptorshiMstGroupModule'
            },
            {
                path: 'mst-account',
                loadChildren: './mst-account-extended/mst-account-extended.module#SoptorshiMstAccountModule'
            },
            {
                path: 'account-balance',
                loadChildren: './account-balance-extended/account-balance-extended.module#SoptorshiAccountBalanceModule'
            },
            {
                path: 'monthly-balance',
                loadChildren: './monthly-balance/monthly-balance.module#SoptorshiMonthlyBalanceModule'
            },
            {
                path: 'predefined-narration',
                loadChildren: './predefined-narration-extended/predefined-narration-extended.module#SoptorshiPredefinedNarrationModule'
            },
            {
                path: 'conversion-factor',
                loadChildren: './conversion-factor-extended/conversion-factor-extended.module#SoptorshiConversionFactorModule'
            },
            {
                path: 'cheque-register',
                loadChildren: './cheque-register/cheque-register.module#SoptorshiChequeRegisterModule'
            },
            {
                path: 'dt-transaction',
                loadChildren: './dt-transaction-extended/dt-transaction-extended.module#SoptorshiDtTransactionModule'
            },
            {
                path: 'customer',
                loadChildren: './customer/customer.module#SoptorshiCustomerModule'
            },
            {
                path: 'creditor-ledger',
                loadChildren: './creditor-ledger/creditor-ledger.module#SoptorshiCreditorLedgerModule'
            },
            {
                path: 'debtor-ledger',
                loadChildren: './debtor-ledger/debtor-ledger.module#SoptorshiDebtorLedgerModule'
            },
            {
                path: 'system-account-map',
                loadChildren: './system-account-map-extended/system-account-map-extended.module#SoptorshiSystemAccountMapModule'
            },
            {
                path: 'system-group-map',
                loadChildren: './system-group-map-extended/system-group-map-extended.module#SoptorshiSystemGroupMapExtendedModule'
            },
            {
                path: 'academic-information-attachment',
                loadChildren:
                    './academic-information-attachment/academic-information-attachment.module#SoptorshiAcademicInformationAttachmentModule'
            },
            {
                path: 'experience-information-attachment',
                loadChildren:
                    './experience-information-attachment/experience-information-attachment.module#SoptorshiExperienceInformationAttachmentModule'
            },
            {
                path: 'training-information-attachment',
                loadChildren:
                    './training-information-attachment/training-information-attachment.module#SoptorshiTrainingInformationAttachmentModule'
            },
            {
                path: 'voucher-number-control',
                loadChildren: './voucher-number-control/voucher-number-control.module#SoptorshiVoucherNumberControlModule'
            },
            {
                path: 'journal-voucher',
                loadChildren: './journal-voucher-extended/journal-voucher-extended.module#SoptorshiJournalVoucherModule'
            },
            {
                path: 'payment-voucher',
                loadChildren: './payment-voucher-extended/payment-voucher-extended.module#SoptorshiPaymentVoucherExtendedModule'
            },
            {
                path: 'receipt-voucher',
                loadChildren: './receipt-voucher-extended/receipt-voucher-extended.module#SoptorshiReceiptVoucherModule'
            },
            {
                path: 'contra-voucher',
                loadChildren: './contra-voucher-extended/contra-voucher-extended.module#SoptorshiContraVoucherModule'
            },
            {
                path: 'system-group-map',
                loadChildren: './system-group-map/system-group-map.module#SoptorshiSystemGroupMapExtendedModule'
            },
            {
                path: 'commercial-purchase-order',
                loadChildren:
                    './commercial-purchase-order-extended/commercial-purchase-order-extended.module#SoptorshiCommercialPurchaseOrderExtendedModule'
            },
            {
                path: 'commercial-purchase-order-item',
                loadChildren:
                    './commercial-purchase-order-item-extended/commercial-purchase-order-item-extended.module#SoptorshiCommercialPurchaseOrderItemExtendedModule'
            },
            {
                path: 'commercial-proforma-invoice',
                loadChildren:
                    './commercial-proforma-invoice-extended/commercial-proforma-invoice-extended.module#SoptorshiCommercialProformaInvoiceExtendedModule'
            },
            {
                path: 'commercial-payment-info',
                loadChildren:
                    './commercial-payment-info-extended/commercial-payment-info-extended.module#SoptorshiCommercialPaymentInfoExtendedModule'
            },
            {
                path: 'commercial-packaging',
                loadChildren:
                    './commercial-packaging-extended/commercial-packaging-extended.module#SoptorshiCommercialPackagingExtendedModule'
            },
            {
                path: 'commercial-packaging-details',
                loadChildren:
                    './commercial-packaging-details-extended/commercial-packaging-details-extended.module#SoptorshiCommercialPackagingDetailsExtendedModule'
            },
            {
                path: 'commercial-work-order',
                loadChildren:
                    './commercial-work-order-extended/commercial-work-order-extended.module#SoptorshiCommercialWorkOrderExtendedModule'
            },
            {
                path: 'commercial-work-order-details',
                loadChildren:
                    './commercial-work-order-details-extended/commercial-work-order-details-extended.module#SoptorshiCommercialWorkOrderDetailsExtendedModule'
            },
            {
                path: 'commercial-invoice',
                loadChildren: './commercial-invoice-extended/commercial-invoice-extended.module#SoptorshiCommercialInvoiceExtendedModule'
            },
            {
                path: 'commercial-po-status',
                loadChildren:
                    './commercial-po-status-extended/commercial-po-status-extended.module#SoptorshiCommercialPoStatusExtendedModule'
            },
            {
                path: 'commercial-attachment',
                loadChildren:
                    './commercial-attachment-extended/commercial-attachment-extended.module#SoptorshiCommercialAttachmentExtendedModule'
            },
            {
                path: 'supply-zone',
                loadChildren: './supply-zone/supply-zone.module#SoptorshiSupplyZoneModule'
            },
            {
                path: 'supply-area',
                loadChildren: './supply-area/supply-area.module#SoptorshiSupplyAreaModule'
            },
            {
                path: 'supply-area-manager',
                loadChildren: './supply-area-manager/supply-area-manager.module#SoptorshiSupplyAreaManagerModule'
            },
            {
                path: 'supply-sales-representative',
                loadChildren: './supply-sales-representative/supply-sales-representative.module#SoptorshiSupplySalesRepresentativeModule'
            },
            {
                path: 'supply-shop',
                loadChildren: './supply-shop/supply-shop.module#SoptorshiSupplyShopModule'
            },
            {
                path: 'supply-order',
                loadChildren: './supply-order/supply-order.module#SoptorshiSupplyOrderModule'
            },
            {
                path: 'supply-order-details',
                loadChildren: './supply-order-details/supply-order-details.module#SoptorshiSupplyOrderDetailsModule'
            },
            {
                path: 'supply-challan',
                loadChildren: './supply-challan/supply-challan.module#SoptorshiSupplyChallanModule'
            },
            {
                path: 'supply-money-collection',
                loadChildren: './supply-money-collection/supply-money-collection.module#SoptorshiSupplyMoneyCollectionModule'
            },
            {
                path: 'special-allowance-time-line',
                loadChildren: './special-allowance-time-line/special-allowance-time-line.module#SoptorshiSpecialAllowanceTimeLineModule'
            }
            /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
        ])
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiEntityModule {}
