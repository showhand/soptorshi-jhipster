/* tslint:disable max-line-length */
import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { SupplyAreaUpdateComponent } from 'app/entities/supply-area/supply-area-update.component';
import { SupplyAreaService } from 'app/entities/supply-area/supply-area.service';
import { SupplyArea } from 'app/shared/model/supply-area.model';

describe('Component Tests', () => {
    describe('SupplyArea Management Update Component', () => {
        let comp: SupplyAreaUpdateComponent;
        let fixture: ComponentFixture<SupplyAreaUpdateComponent>;
        let service: SupplyAreaService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [SupplyAreaUpdateComponent]
            })
                .overrideTemplate(SupplyAreaUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SupplyAreaUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SupplyAreaService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new SupplyArea(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.supplyArea = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new SupplyArea();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.supplyArea = entity;
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
