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

const ENTITY_STATES = [...purchaseCommitteeRoute, ...purchaseCommitteePopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
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
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiPurchaseCommitteeModule {}
