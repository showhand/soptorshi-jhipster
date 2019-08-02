import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    InventoryLocationComponent,
    InventoryLocationDetailComponent,
    InventoryLocationUpdateComponent,
    InventoryLocationDeletePopupComponent,
    InventoryLocationDeleteDialogComponent,
    inventoryLocationRoute,
    inventoryLocationPopupRoute
} from './';

const ENTITY_STATES = [...inventoryLocationRoute, ...inventoryLocationPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        InventoryLocationComponent,
        InventoryLocationDetailComponent,
        InventoryLocationUpdateComponent,
        InventoryLocationDeleteDialogComponent,
        InventoryLocationDeletePopupComponent
    ],
    entryComponents: [
        InventoryLocationComponent,
        InventoryLocationUpdateComponent,
        InventoryLocationDeleteDialogComponent,
        InventoryLocationDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiInventoryLocationModule {}
