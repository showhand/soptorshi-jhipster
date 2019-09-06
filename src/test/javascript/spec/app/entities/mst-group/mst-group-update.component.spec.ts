/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { MstGroupUpdateComponent } from 'app/entities/mst-group/mst-group-update.component';
import { MstGroupService } from 'app/entities/mst-group/mst-group.service';
import { MstGroup } from 'app/shared/model/mst-group.model';

describe('Component Tests', () => {
    describe('MstGroup Management Update Component', () => {
        let comp: MstGroupUpdateComponent;
        let fixture: ComponentFixture<MstGroupUpdateComponent>;
        let service: MstGroupService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [MstGroupUpdateComponent]
            })
                .overrideTemplate(MstGroupUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(MstGroupUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MstGroupService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new MstGroup(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.mstGroup = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new MstGroup();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.mstGroup = entity;
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
