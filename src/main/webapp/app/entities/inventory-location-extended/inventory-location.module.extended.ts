import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    InventoryLocationComponentExtended,
    InventoryLocationDetailComponentExtended,
    InventoryLocationUpdateComponentExtended,
    InventoryLocationDeletePopupComponentExtended,
    InventoryLocationDeleteDialogComponentExtended,
    inventoryLocationRouteExtended,
    inventoryLocationPopupRouteExtended
} from './';

const ENTITY_STATES = [...inventoryLocationRouteExtended, ...inventoryLocationPopupRouteExtended];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        InventoryLocationComponentExtended,
        InventoryLocationDetailComponentExtended,
        InventoryLocationUpdateComponentExtended,
        InventoryLocationDeleteDialogComponentExtended,
        InventoryLocationDeletePopupComponentExtended
    ],
    entryComponents: [
        InventoryLocationComponentExtended,
        InventoryLocationUpdateComponentExtended,
        InventoryLocationDeleteDialogComponentExtended,
        InventoryLocationDeletePopupComponentExtended
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiInventoryLocationModuleExtended {}
