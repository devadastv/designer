import React from 'react';

const Paths = ({ paths }) => {

    const pathList = paths.length ? (
        paths.map(path => {
            return (
                <div className="collection-item" key={path.path}>
                    <span>{path.path}</span>
                </div>
            )
        })
    ) : (
        <p className="center">There are no paths available</p>
    )
    return (
        <div className="collection">
            {pathList}
        </div>
    )
}

export default Paths;