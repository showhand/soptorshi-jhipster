import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    SupplyOrderDetailsComponent,
    SupplyOrderDetailsDeleteDialogComponent,
    SupplyOrderDetailsDeletePopupComponent,
    SupplyOrderDetailsDetailComponent,
    supplyOrderDetailsPopupRoute,
    supplyOrderDetailsRoute,
    SupplyOrderDetailsUpdateComponent
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
