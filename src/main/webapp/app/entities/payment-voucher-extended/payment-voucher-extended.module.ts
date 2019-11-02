import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    PaymentVoucherComponent,
    PaymentVoucherDeleteDialogComponent,
    PaymentVoucherDeletePopupComponent,
    PaymentVoucherDetailComponent,
    PaymentVoucherUpdateComponent
} from 'app/entities/payment-voucher';
import {
    paymentVoucherExtendedPopupRoute,
    paymentVoucherExtendedRoute
} from 'app/entities/payment-voucher-extended/payment-voucher-extended.route';
import { PaymentVoucherExtendedComponent } from 'app/entities/payment-voucher-extended/payment-voucher-extended.component';
import { PaymentVoucherExtendedDetailComponent } from 'app/entities/payment-voucher-extended/payment-voucher-extended-detail.component';
import { PaymentVoucherExtendedUpdateComponent } from 'app/entities/payment-voucher-extended/payment-voucher-extended-update.component';
import { PaymentVoucherTransactionComponent } from 'app/entities/payment-voucher-extended/payment-voucher-transaction.component';
import { PaymentVoucherTransactionUpdateComponent } from 'app/entities/payment-voucher-extended/payment-voucher-transaction-update.component';

const ENTITY_STATES = [...paymentVoucherExtendedRoute, ...paymentVoucherExtendedPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PaymentVoucherComponent,
        PaymentVoucherDetailComponent,
        PaymentVoucherUpdateComponent,
        PaymentVoucherExtendedComponent,
        PaymentVoucherExtendedDetailComponent,
        PaymentVoucherExtendedUpdateComponent,
        PaymentVoucherDeleteDialogComponent,
        PaymentVoucherDeletePopupComponent,
        PaymentVoucherTransactionComponent,
        PaymentVoucherTransactionUpdateComponent
    ],
    entryComponents: [
        PaymentVoucherComponent,
        PaymentVoucherUpdateComponent,
        PaymentVoucherDeleteDialogComponent,
        PaymentVoucherDeletePopupComponent,
        PaymentVoucherTransactionUpdateComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiPaymentVoucherExtendedModule {}
