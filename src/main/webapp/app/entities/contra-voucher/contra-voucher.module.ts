import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    ContraVoucherComponent,
    ContraVoucherDetailComponent,
    ContraVoucherUpdateComponent,
    ContraVoucherDeletePopupComponent,
    ContraVoucherDeleteDialogComponent,
    contraVoucherRoute,
    contraVoucherPopupRoute
} from './';

const ENTITY_STATES = [...contraVoucherRoute, ...contraVoucherPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    /* declarations: [
        ContraVoucherComponent,
        ContraVoucherDetailComponent,
        ContraVoucherUpdateComponent,
        ContraVoucherDeleteDialogComponent,
        ContraVoucherDeletePopupComponent
    ],
    entryComponents: [
        ContraVoucherComponent,
        ContraVoucherUpdateComponent,
        ContraVoucherDeleteDialogComponent,
        ContraVoucherDeletePopupComponent
    ],*/
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiContraVoucherModule {}
