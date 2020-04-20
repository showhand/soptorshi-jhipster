/* tslint:disable max-line-length */
import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { SupplyZoneManagerUpdateComponent } from 'app/entities/supply-zone-manager/supply-zone-manager-update.component';
import { SupplyZoneManagerService } from 'app/entities/supply-zone-manager/supply-zone-manager.service';
import { SupplyZoneManager } from 'app/shared/model/supply-zone-manager.model';

describe('Component Tests', () => {
    describe('SupplyZoneManager Management Update Component', () => {
        let comp: SupplyZoneManagerUpdateComponent;
        let fixture: ComponentFixture<SupplyZoneManagerUpdateComponent>;
        let service: SupplyZoneManagerService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [SupplyZoneManagerUpdateComponent]
            })
                .overrideTemplate(SupplyZoneManagerUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SupplyZoneManagerUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SupplyZoneManagerService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new SupplyZoneManager(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.supplyZoneManager = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new SupplyZoneManager();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.supplyZoneManager = entity;
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
