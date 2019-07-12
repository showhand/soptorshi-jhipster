import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    PurchaseCommitteeComponent,
    PurchaseCommitteeDetailComponent,
    PurchaseCommitteeUpdateComponent,
    PurchaseCommitteeDeletePopupComponent,
    PurchaseCommitteeDeleteDialogComponent,
    purchaseCommitteeRoute,
    purchaseCommitteePopupRoute
} from './';
import { Select2Module } from 'ng2-select2';
import { AutoCompleteModule } from 'primeng/primeng';

const ENTITY_STATES = [...purchaseCommitteeRoute, ...purchaseCommitteePopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES), Select2Module, AutoCompleteModule],
    /* declarations: [
        PurchaseCommitteeComponent,
        PurchaseCommitteeDetailComponent,
        PurchaseCommitteeUpdateComponent,
        PurchaseCommitteeDeleteDialogComponent,
        PurchaseCommitteeDeletePopupComponent
    ],
    entryComponents: [
        PurchaseCommitteeComponent,
        PurchaseCommitteeUpdateComponent,
        PurchaseCommitteeDeleteDialogComponent,
        PurchaseCommitteeDeletePopupComponent
    ],*/
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiPurchaseCommitteeModule {}
