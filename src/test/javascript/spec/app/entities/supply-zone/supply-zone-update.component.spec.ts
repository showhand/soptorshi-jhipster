/* tslint:disable max-line-length */
import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { SupplyZoneUpdateComponent } from 'app/entities/supply-zone/supply-zone-update.component';
import { SupplyZoneService } from 'app/entities/supply-zone/supply-zone.service';
import { SupplyZone } from 'app/shared/model/supply-zone.model';

describe('Component Tests', () => {
    describe('SupplyZone Management Update Component', () => {
        let comp: SupplyZoneUpdateComponent;
        let fixture: ComponentFixture<SupplyZoneUpdateComponent>;
        let service: SupplyZoneService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [SupplyZoneUpdateComponent]
            })
                .overrideTemplate(SupplyZoneUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SupplyZoneUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SupplyZoneService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new SupplyZone(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.supplyZone = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new SupplyZone();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.supplyZone = entity;
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
