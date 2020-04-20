import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    ReceiptVoucherComponent,
    ReceiptVoucherDetailComponent,
    ReceiptVoucherUpdateComponent,
    ReceiptVoucherDeletePopupComponent,
    ReceiptVoucherDeleteDialogComponent,
    receiptVoucherRoute,
    receiptVoucherPopupRoute
} from './';

const ENTITY_STATES = [...receiptVoucherRoute, ...receiptVoucherPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    /*  declarations: [
        ReceiptVoucherComponent,
        ReceiptVoucherDetailComponent,
        ReceiptVoucherUpdateComponent,
        ReceiptVoucherDeleteDialogComponent,
        ReceiptVoucherDeletePopupComponent
    ],
    entryComponents: [
        ReceiptVoucherComponent,
        ReceiptVoucherUpdateComponent,
        ReceiptVoucherDeleteDialogComponent,
        ReceiptVoucherDeletePopupComponent
    ],*/
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiReceiptVoucherModule {}
