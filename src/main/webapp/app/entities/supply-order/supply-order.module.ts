import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    SupplyOrderComponent,
    SupplyOrderDeleteDialogComponent,
    SupplyOrderDeletePopupComponent,
    SupplyOrderDetailComponent,
    supplyOrderPopupRoute,
    supplyOrderRoute,
    SupplyOrderUpdateComponent
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
