/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { InventorySubLocationUpdateComponent } from 'app/entities/inventory-sub-location/inventory-sub-location-update.component';
import { InventorySubLocationService } from 'app/entities/inventory-sub-location/inventory-sub-location.service';
import { InventorySubLocation } from 'app/shared/model/inventory-sub-location.model';

describe('Component Tests', () => {
    describe('InventorySubLocation Management Update Component', () => {
        let comp: InventorySubLocationUpdateComponent;
        let fixture: ComponentFixture<InventorySubLocationUpdateComponent>;
        let service: InventorySubLocationService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [InventorySubLocationUpdateComponent]
            })
                .overrideTemplate(InventorySubLocationUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(InventorySubLocationUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InventorySubLocationService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new InventorySubLocation(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.inventorySubLocation = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new InventorySubLocation();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.inventorySubLocation = entity;
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
