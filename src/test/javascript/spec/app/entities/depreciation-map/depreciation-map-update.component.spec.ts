/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { DepreciationMapUpdateComponent } from 'app/entities/depreciation-map/depreciation-map-update.component';
import { DepreciationMapService } from 'app/entities/depreciation-map/depreciation-map.service';
import { DepreciationMap } from 'app/shared/model/depreciation-map.model';

describe('Component Tests', () => {
    describe('DepreciationMap Management Update Component', () => {
        let comp: DepreciationMapUpdateComponent;
        let fixture: ComponentFixture<DepreciationMapUpdateComponent>;
        let service: DepreciationMapService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [DepreciationMapUpdateComponent]
            })
                .overrideTemplate(DepreciationMapUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DepreciationMapUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DepreciationMapService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new DepreciationMap(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.depreciationMap = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new DepreciationMap();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.depreciationMap = entity;
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
