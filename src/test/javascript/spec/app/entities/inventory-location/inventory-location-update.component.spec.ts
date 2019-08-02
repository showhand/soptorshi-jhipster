/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { InventoryLocationUpdateComponent } from 'app/entities/inventory-location/inventory-location-update.component';
import { InventoryLocationService } from 'app/entities/inventory-location/inventory-location.service';
import { InventoryLocation } from 'app/shared/model/inventory-location.model';

describe('Component Tests', () => {
    describe('InventoryLocation Management Update Component', () => {
        let comp: InventoryLocationUpdateComponent;
        let fixture: ComponentFixture<InventoryLocationUpdateComponent>;
        let service: InventoryLocationService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [InventoryLocationUpdateComponent]
            })
                .overrideTemplate(InventoryLocationUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(InventoryLocationUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InventoryLocationService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new InventoryLocation(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.inventoryLocation = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new InventoryLocation();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.inventoryLocation = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
