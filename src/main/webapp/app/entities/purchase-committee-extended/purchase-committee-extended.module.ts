import {
    PurchaseCommitteeComponent,
    PurchaseCommitteeDeleteDialogComponent,
    PurchaseCommitteeDeletePopupComponent,
    PurchaseCommitteeDetailComponent,
    purchaseCommitteePopupRoute,
    purchaseCommitteeRoute,
    PurchaseCommitteeUpdateComponent
} from 'app/entities/purchase-committee';
import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { SoptorshiSharedModule } from 'app/shared';
import { RouterModule } from '@angular/router';
import { Select2Module } from 'ng2-select2';
import { AutoCompleteModule } from 'primeng/primeng';
import {
    purchaseCommitteeExtendedPopupRoute,
    purchaseCommitteeExtendedRoute
} from 'app/entities/purchase-committee-extended/purchase-committee-extended.route';
import { PurchaseCommitteeExtendedComponent } from 'app/entities/purchase-committee-extended/purchase-committee-extended.component';
import { PurchaseCommitteeExtendedUpdateComponent } from 'app/entities/purchase-committee-extended/purchase-committee-extended-update.component';

const ENTITY_STATES = [...purchaseCommitteeExtendedRoute, ...purchaseCommitteeExtendedPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES), Select2Module, AutoCompleteModule],
    declarations: [
        PurchaseCommitteeComponent,
        PurchaseCommitteeDetailComponent,
        PurchaseCommitteeUpdateComponent,
        PurchaseCommitteeExtendedComponent,
        PurchaseCommitteeDetailComponent,
        PurchaseCommitteeExtendedUpdateComponent,
        PurchaseCommitteeDeleteDialogComponent,
        PurchaseCommitteeDeletePopupComponent
    ],
    entryComponents: [
        PurchaseCommitteeExtendedComponent,
        PurchaseCommitteeExtendedUpdateComponent,
        PurchaseCommitteeDeleteDialogComponent,
        PurchaseCommitteeDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiPurchaseCommitteeExtendedModule {}
