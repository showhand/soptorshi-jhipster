import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    SupplyOrderComponent,
    SupplyOrderDetailComponent,
    SupplyOrderUpdateComponent,
    SupplyOrderDeletePopupComponent,
    SupplyOrderDeleteDialogComponent,
    supplyOrderRoute,
    supplyOrderPopupRoute
} from './';

const ENTITY_STATES = [...supplyOrderRoute, ...supplyOrderPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SupplyOrderComponent,
        SupplyOrderDetailComponent,
        SupplyOrderUpdateComponent,
        SupplyOrderDeleteDialogComponent,
        SupplyOrderDeletePopupComponent
    ],
    entryComponents: [SupplyOrderComponent, SupplyOrderUpdateComponent, SupplyOrderDeleteDialogComponent, SupplyOrderDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiSupplyOrderModule {}
