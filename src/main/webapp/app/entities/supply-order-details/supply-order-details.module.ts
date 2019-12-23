import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    SupplyOrderDetailsComponent,
    SupplyOrderDetailsDetailComponent,
    SupplyOrderDetailsUpdateComponent,
    SupplyOrderDetailsDeletePopupComponent,
    SupplyOrderDetailsDeleteDialogComponent,
    supplyOrderDetailsRoute,
    supplyOrderDetailsPopupRoute
} from './';

const ENTITY_STATES = [...supplyOrderDetailsRoute, ...supplyOrderDetailsPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SupplyOrderDetailsComponent,
        SupplyOrderDetailsDetailComponent,
        SupplyOrderDetailsUpdateComponent,
        SupplyOrderDetailsDeleteDialogComponent,
        SupplyOrderDetailsDeletePopupComponent
    ],
    entryComponents: [
        SupplyOrderDetailsComponent,
        SupplyOrderDetailsUpdateComponent,
        SupplyOrderDetailsDeleteDialogComponent,
        SupplyOrderDetailsDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiSupplyOrderDetailsModule {}
